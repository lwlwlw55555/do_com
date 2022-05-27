package com.domdd.ioc.myioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lw
 * @date 2022/5/27 1:58 下午
 */
@Target(ElementType.TYPE)//表示该注解作用再类上
@Retention(RetentionPolicy.RUNTIME)//注解在运行时生效
public @interface MyComponent {
    String value() default "";// 指定容器BeanName
}