package com.domdd.proxy.jdk;

import java.lang.reflect.InvocationHandler;

/**
 * @author lw
 * @date 2022/4/25 3:23 下午
 */
public class DoProxyFactory {
    public static <T> T newInstance(Class<T> clazz) {
        InvocationHandler h = new DoProxy();
        return (T) java.lang.reflect.Proxy.newProxyInstance(DoProxyFactory.class.getClassLoader(), new Class[]{clazz}, h);
    }
}


