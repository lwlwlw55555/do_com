package com.domdd.proxy.cglib;

/**
 * @author lw
 * @date 2022/5/18 11:21 上午
 */
public class HelloService {
    public HelloService() {
        System.out.println("HelloService Constructor...");
    }

    /**
     * 该方法不能被子类覆盖，Cglib是无法代理final修饰的方法的
     */
    final public String sayOthers(String name) {
        System.out.println("HelloService...sayOthers..." + name);
        return null;
    }

    public void sayHello() {
        System.out.println("HelloService...sayHello...");
    }
}
