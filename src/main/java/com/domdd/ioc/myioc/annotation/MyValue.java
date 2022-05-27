package com.domdd.ioc.myioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lw
 * @date 2022/5/27 2:08 下午
 */
@Target(ElementType.FIELD)//表示该注解作用在成员变量上
@Retention(RetentionPolicy.RUNTIME)//运行时注解生效
public @interface MyValue {
    String value();//Value必须手动赋值
}


