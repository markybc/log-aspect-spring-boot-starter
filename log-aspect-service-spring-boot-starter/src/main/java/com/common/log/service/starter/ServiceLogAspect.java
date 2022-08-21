package com.common.log.service.starter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.UUID;

/**
 * service 日志 切面
 */
@Slf4j
@Aspect
public class ServiceLogAspect {

    /**
     * 拦截控制层的所有 Service
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void logService() {
    }

    private static ThreadLocal<String> threadLocalMethod = ThreadLocal.withInitial(() -> null);
    /**
     * 方法执行前后 拦截
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logService()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String logId = ThreadContext.get("logId");
        if (isBlank(logId)) {
            logId = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
            ThreadContext.put("logId", logId);
            threadLocalMethod.set(pjp.getSignature().toString());
        }

        //方法消耗时间
        long start = System.currentTimeMillis();

        Object obj = null;
        try {
            obj = pjp.proceed();
        } finally {
            String returnValue = null;
            if (obj != null) {
                if (obj instanceof List) {
                    returnValue = " size : " + JSONObject.parseArray(JSONObject.toJSONString(obj)).size();
                } else {
                    returnValue = JSONObject.toJSONString(obj);
                }
            }
            long end = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder();
            builder.append("====>>>> service 调用 <<<==== {URL:[")
                    .append("Args:").append(JSONObject.toJSONString(pjp.getArgs())).append(",")
                    .append("ReturnValue:[").append(returnValue).append("],")
                    .append("Time:[").append(end - start).append("ms],")
                    .append("MethodName:[").append(pjp.getSignature()).append("]}");
            log.info(builder.toString());
            if (pjp.getSignature().toString().equals(threadLocalMethod.get())){
                ThreadContext.clearAll();
                threadLocalMethod.set(null);
            }

        }
        return obj;

    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}

