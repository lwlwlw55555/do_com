/**
 * 
 */
package com.domdd.core.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {
	private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.domdd.controller.*.*..*(..))")
    public void webLog(){}

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null) {
        	return ;
        }
        HttpServletRequest request = attributes.getRequest();
        
        logger.info("[WebLog] URL : " + request.getRequestURL().toString() +",CLASS_METHOD : "
        			+ joinPoint.getSignature().getDeclaringTypeName() + "." 
        			+ joinPoint.getSignature().getName() + "," 
        			+ request.getMethod() +",  ARGS : " 
        			+ (joinPoint.getArgs().length>0?joinPoint.getArgs()[0]:"")
        			+ "  RESPONSE : " + JSONObject.toJSONString(ret));
    }
}
