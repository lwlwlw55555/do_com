package com.domdd.service.fit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Builder<T> {

    private final Supplier<T> instanceProvider;

    private final List<Consumer<T>> modifiers = new ArrayList<>();

    public Builder(Supplier<T> instanceProvider) {
        this.instanceProvider = instanceProvider;
    }

    public static <T> Builder<T> of(Supplier<T> instanceProvider) {
        return new Builder<>(instanceProvider);
    }

    /**
     * 支持接收1个参数
     */
    public <P1> Builder<T> with(BiConsumer<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    /**
     * 支持接收2个参数
     */
    public <P1, P2> Builder<T> with(LwTest<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }

    //    public <P1, P2> Builder<T> with(Consumer2<T, P1, P2> consumer2, P1 p1, P2 p2) {
//        Consumer<T> c = instance -> consumer2.accept(instance, p1, p2);
//        modifiers.add(c);
//        return this;
//    }
//    public Builder<T> with(LwTest lwTest, Object... objects) {
//        Consumer<T> c = instance -> lwTest.accept(instance, objects);
//        modifiers.add(c);
//        return this;
//    }
    public T build() {
        T value = instanceProvider.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }

    public interface LwTest<T, V, R> {
        void accept(T p1, V p2, R p3);
    }
//
//    @FunctionalInterface
//    public interface Consumer1<T, P1> {
//        /**
//         * T 本Builder要构造的对象的类型
//         * P1 本Builder要构造的对象的setters方法参数的类型
//         *
//         * @param t
//         * @param p1
//         */
//        void accept(T t, P1 p1);
//    }
//
//    @FunctionalInterface
//    public interface Consumer2<T, P1, P2> {
//        void accept(T t, P1 p1, P2 p2);
//    }

    /**
     * 测试demo
     */
//    public static void main(String[] args) throws ParseException {
//        Person person1 = Builder.of(Person::new).with(Person::setName, "boildwater").with(Person::setAge, 18).build();
//        System.out.println(person1.getName() + ":" + person1.getAge());
//
//        Person person2 = Builder.of(Person::new).with(Person::setNameAndBirthday, "boildwater", DateUtils.parseDate("1997-11-07", "yyyy-MM-dd")).build();
//        System.out.println(person2.name + ":" + person2.getBirthday());
//    }

    public static class Person {
        private String name;
        private Integer age;
        private Date birthday;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public static void setAge2(String s) {
            System.out.println("I am setAge2" + s);
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        /**
         * 一次设置多个参数
         */
        public void setNameAndBirthday(String name, Date birthday) {
            this.name = name;
            this.birthday = birthday;
        }
    }
}



