package com.common.log.feign.starter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

@Slf4j
public class FeignLogger extends feign.Logger {
    static ThreadLocal<Map<String, String>> logContext = new ThreadLocal<>();
    static String PATH = "path";
    static String METHOD = "method";
    static String REQUEST_BODY = "body";

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        Map<String, String> logMap = new HashMap<>(3);
        logMap.put(PATH, request.url());
        logMap.put(METHOD, request.httpMethod().name());
        logMap.put(REQUEST_BODY, request.body() == null ? null :
                request.charset() == null ? null : new String(request.body(), request.charset()));
        logContext.set(logMap);
    }
    @Override
    protected Response logAndRebufferResponse(
            String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        Map<String, String> requestParams = logContext.get();
        logContext.remove();
        String returnValue = "";

        // 返回参数
        if (response.body() != null && !(response.status() == 204 || response.status() == 205)) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());

            if (bodyData.length > 0) {
                String responseBody = decodeOrDefault(bodyData, UTF_8, "Binary data");
                responseBody = responseBody.replaceAll("\\s*|\t|\r|\n", "");
                returnValue = getJsonStringValue(JSONObject.toJSONString(responseBody));
            }
            response = response.toBuilder().body(bodyData).build();
        }
        StringBuilder builder = new StringBuilder().append("====>>>> feign 调用 <<<==== {")
                .append("URL:["+requestParams.get(PATH)).append("]")
                .append(",RequestMethod:[").append(requestParams.get(METHOD)).append("]")
                .append(",Args:").append(JSONObject.toJSONString(requestParams.get(REQUEST_BODY))).append("")
                .append(",ReturnValue:[").append(returnValue).append("]")
                .append(",Time:[").append(elapsedTime).append("ms]")
                .append("}");
        log.info(builder.toString());
        return response;
    }
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        Map<String, String> requestParams = logContext.get();
        StringBuilder builder = new StringBuilder().append("====>>>> feign 调用 <<<==== {")
                .append("URL:["+requestParams.get(PATH)).append("]")
                .append(",RequestMethod:[").append(requestParams.get(METHOD)).append("]")
                .append(",Args:[").append(JSONObject.toJSONString(requestParams.get(REQUEST_BODY))).append("]")
                .append(",Time:[").append(elapsedTime).append("ms]")
                .append("}");
        log.info(builder.toString());

        logContext.remove();
        return ioe;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (log.isInfoEnabled()) {
            log.info(String.format(methodTag(configKey) + format, args));
        }
    }

    public  String getJsonStringValue(String str) {
        String returnValue = str;
        try {
            JSONObject jsonObject = JSONObject.parseObject(str);
            extractedObj(jsonObject);
            returnValue = jsonObject.toJSONString();
        } catch (Exception e) {
        }
        return returnValue;
    }

    private static void extractedObj(JSONObject logObj) {
        logObj.getInnerMap().forEach((s, o) -> {
            if (o instanceof JSONObject){
                extractedObj((JSONObject) o);
            }else if (o instanceof  JSONArray){
                JSONArray array = (JSONArray) o;
                logObj.put(s,"size="+array.size());
            }
        });
    }

}

