package com.domdd.service.proxy;

import com.domdd.proxy.cglib.HelloService;
import com.domdd.proxy.cglib.MyMethodInterceptor;
import org.junit.Test;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author lw
 * @date 2022/5/18 11:23 上午
 */
public class CglibTest {


    /**
     * 代理对象调用sayHello，然后调用拦截器intercept，
     * 然后执行拦截器中的前面代码，然后执行invokeSuper，
     * 会执行CGLIB$sayHello$0方法，然后调用父类sayHello，
     * 然后执行拦截器后面代码
     */
    public static void main(String[] args) {
        //代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/lw/Code/proxy");
        //通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        //设置enhancer对象的父类
        enhancer.setSuperclass(HelloService.class);
        //设置enhancer的回调对象
//        enhancer.setInterfaces();
        enhancer.setCallback(new MyMethodInterceptor());
        //创建代理对象
        HelloService proxy = (HelloService) enhancer.create();
        //通过代理对象调用目标方法
        proxy.sayHello();

        System.out.println("---------");

        proxy.sayOthers("111");
    }
}
