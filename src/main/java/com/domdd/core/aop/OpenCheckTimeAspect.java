//package com.domdd.core.aop;
//
//import com.alibaba.fastjson.JSON;
//import com.domdd.controller.req.OpenBaseReq;
//import com.domdd.core.DanengOpenPropertiesDo;
//import com.domdd.core.ServiceException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.Objects;
//import java.util.TreeMap;
//
//import static cn.hutool.extra.validation.ValidationUtil.validate;
//
//@Aspect
//@Slf4j
//@Component
//@Order(2)
//public class OpenCheckTimeAspect {
//    @Resource
//    private DanengOpenPropertiesDo danengOpenPropertiesDo;
//
//    @Pointcut("execution(* com.domdd.controller.OpenController.*(..))")
//    private void openPut() {
//
//    }
//
//    @Around("openPut()")
//    public void openPut(ProceedingJoinPoint point) {
//        try {
//
//            point.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//}
