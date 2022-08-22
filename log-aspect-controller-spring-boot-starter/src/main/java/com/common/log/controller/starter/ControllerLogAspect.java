package com.common.log.controller.starter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 控制层 日志 切面
 */
@Slf4j
@Aspect
public class ControllerLogAspect {

    private HttpServletRequest request;

    public ControllerLogAspect(HttpServletRequest request) {
        this.request = request;
    }

    private ControllerLogAspect() {
    }

    /**
     * 拦截控制层
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logController() {
    }

    /**
     * 方法执行前后 拦截
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logController()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String logId = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        ThreadContext.put("logId", logId);

        //方法消耗时间
        long start = System.currentTimeMillis();

        Object obj = null;
        try {
            obj = pjp.proceed();
        } finally {
            String returnValue = null;
            if (obj != null) {
                if (obj instanceof List) {
                    List list = (List) obj;
                    returnValue = " size=" + list.size();
                } else {
                    returnValue = getJsonStringValue(JSONObject.toJSONString(obj));
                }
            }

            long end = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder();
            builder.append("====>>>> controller 调用 <<<==== {")
                    .append("URL:["+request.getRequestURI()).append("],")
                    .append("RequestMethod:[").append(request.getMethod()).append("],")
                    .append("Args:").append(JSONObject.toJSONString(pjp.getArgs())).append(",")
                    .append("ReturnValue:[").append(returnValue).append("],")
                    .append("Time:[").append(end - start).append("ms],")
                    .append("MethodName:[").append(pjp.getSignature()).append("]}");
            log.info(builder.toString());

            ThreadContext.clearAll();
        }
        return obj;

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
            }else if (o instanceof JSONArray){
                JSONArray array = (JSONArray) o;
                logObj.put(s,"size="+array.size());
            }
        });
    }
}

