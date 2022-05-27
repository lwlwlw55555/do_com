//package com.domdd.core.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * @author lw
// * @date 2022/5/13 3:41 下午
// */
//@Aspect
//@Slf4j
//@Component
//@Order(2)
//public class PreNormalDbAop {
//
//    @Pointcut("execution(* com.domdd.service.lw.NormalTransaction.testNormalTransaction(..)))")
//    public void myPoint() {
//    }
//
//// 以下为JDK动态代理
////    @Pointcut("target(com.domdd.core.aop.PreNormalAopAbstract) && execution(* com.domdd.core.aop.PreNormalAopAbstract.testNormalTransaction(..))")
////    public void myPoint() {
////    }
//
//    @Before("myPoint()")
//    public void doBefore() {
//        System.out.println("before normal transaction");
//    }
//}
