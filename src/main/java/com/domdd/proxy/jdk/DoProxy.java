package com.domdd.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author lw
 * @date 2022/4/25 3:18 下午
 */
public class DoProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "test01": {
                return "invoke==>" + UUID.randomUUID().toString();
            }
            case "test02": {
                return "invoke==>" + Arrays.asList(args);
            }
        }
        return null;
    }
}
