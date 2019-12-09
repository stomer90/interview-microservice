package com.stormer.common.logger;

import com.stormer.common.mongo.RequestLog;
import com.stormer.common.mongo.RequestLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Aspect
@Configuration
public class LoggingAspect extends LoggingPointcut{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse httpResponse;

    @Autowired
    RequestLogRepository requestLogRepository;

    @Around("restcontroller()")
    public Object userAdviceController(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestLog requestLog = new RequestLog();
        try {
            LOGGER.info(
                    "\n===========================================================================================\n"
                            + "\n===========================================================================================\n");
            LOGGER.info("[CONTROLLER-START] " + joinPoint.getSignature().getDeclaringTypeName() +
                    "." + joinPoint.getSignature().getName());
            MethodSignature method = (MethodSignature) joinPoint.getStaticPart().getSignature();
            List<String> params = Arrays.asList(method.getParameterNames());
            List<Object> args = logRequest(joinPoint);

            StringBuilder str = new StringBuilder(" Parameters -->  ");
            logParams(params, args, str);

            StringBuilder sb = new StringBuilder(" RequestHeader --> ");
            logHeader(sb);

            requestLog = createMongoLog(joinPoint, requestLog, str, sb);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        long t1 = System.currentTimeMillis();

        Object value = null;
        try {
            value = joinPoint.proceed();
        } finally {
            requestLog.setResponse(String.valueOf(value));
            requestLog.setResponseTime((System.currentTimeMillis() - t1));
            requestLog = this.saveMongodb(requestLog);
            LOGGER.info("[CONTROLLER-END]: {}.{} : {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), System.currentTimeMillis() - t1);
        }

        return value;
    }

    private RequestLog createMongoLog(ProceedingJoinPoint joinPoint, RequestLog requestLog, StringBuilder str, StringBuilder sb) {
        requestLog.setPath(request.getRequestURL().toString());
        requestLog.setClazz(joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName());
        requestLog.setMethod(request.getMethod());
        requestLog.setRequest(str.toString());
        requestLog.setHeader(sb.toString());

        return requestLogRepository.save(requestLog);
    }

    private List<Object> logRequest(ProceedingJoinPoint joinPoint) {
        StringBuilder requestUrl = new StringBuilder(String.format(" RequestURL --> [%s] ", request.getMethod())).append(request.getRequestURL());
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        if (!StringUtils.isEmpty(request.getQueryString())) {
            requestUrl.append("?").append(request.getQueryString());
        }
        LOGGER.info(requestUrl.toString());
        return args;
    }

    private void logParams(List<String> params, List<Object> args, StringBuilder str) {
        Iterator<Object> i1 = args.iterator();
        params.forEach(param -> str.append(", ").append(param).append(" = ").append(i1.next()));
        LOGGER.info(str.toString());
    }

    private void logHeader(StringBuilder sb) {
        if (request.getHeaderNames() != null) {

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                sb.append(" ,").append(key).append(":").append(value);
            }
            String ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null) {
                ip = request.getRemoteAddr();
            }

            sb.append(" ,").append("IP-Client").append(":").append(ip);
            LOGGER.info(sb.toString());
        }
    }

//    @AfterThrowing(pointcut = "restcontroller() || service()", throwing = "ex")
//    public void errorInterceptor(Exception ex) {
//        LOGGER.warn("Error interceptor {}", ex);
//    }

    private RequestLog saveMongodb(RequestLog requestLog) {
        return requestLogRepository.save(requestLog);
    }
}
