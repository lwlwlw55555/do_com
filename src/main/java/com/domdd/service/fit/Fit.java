package com.domdd.service.fit;

//@FunctionalInterface
/**
 *  必须有一个且仅有一个抽象方法
 * 	可以有其他的方法 静态或者默认(default)方法
 */
public interface Fit {
    void lwTest();
//    void lwTest2(Integer i);

    static void lwStatic() {
        System.out.println("lwStatic");
    }

    default void lwDefault() {
        System.out.println("lwDefault");
    }
}
