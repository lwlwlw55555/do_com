package com.domdd.service.fit;

import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.lang.func.VoidFunc0;
import com.domdd.DoMddApplication;
import com.domdd.ioc.myioc.dto.Persion;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

//@Slf4j
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FitTest {
    public static void main(String[] args) {
        Fit fit = () -> {
            System.out.println("1");
        };
        fit.lwTest();

//        Fit fit1 = (key)->{
//            System.out.println(key);
//        };
//        fit1.lwTest2(1);
//
        ApplicationContextFactory factory = ApplicationContextFactory.DEFAULT;
        ConfigurableApplicationContext context = factory.create(WebApplicationType.SERVLET);
        System.out.println(context);

        ApplicationContextFactory factory1 = (key) -> {
            System.out.println(key.name());
            return null;
        };
        ConfigurableApplicationContext context1 = factory1.create(WebApplicationType.NONE);
        System.out.println(context1);

        Supplier supplier = Builder.Person::new;

        Builder<Builder.Person> builder = Builder.of(Builder.Person::new);

        builder.with(Builder.Person::setAge, 1).build();


        BiConsumer<Builder.Person, Integer> consumer = Builder.Person::setAge;

        Consumer<String> consumer1 = Builder.Person::setAge2;


        Builder.Person person = Builder.of(Builder.Person::new).with(Builder.Person::setAge, 1)
                .with(Builder.Person::setNameAndBirthday, "lw", new Date()).build();
        System.out.println(person);

    }

//    public static void main(String[] args) {
//        Fit.lwStatic();
//        shuju((start, end) -> {//重点1  (start,end) 这里只是一个引用
//            System.out.println("开始" + start + "--结束" + end);
//            return Arrays.asList("a", "b");
//        }, 6, 9);
//
//        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
//        testjiekou zhi = ((start, end) -> {//重点4
//            System.out.println("开始" + start + "--结束" + end);
//            List list1 = list.subList(start, end);
//            System.out.println(JSON.toJSONString(list1));
//            return list1;
//        });
//        zhi.test(0, 2);//重点2 真正调用执行
//        /**重点3  输出空，详解:为什么专门加一个这里输出，很多小伙伴以为上面重点4，是接收返回值，大大的误解。应该好好看看函数式接口概念。其实只是相当于比如原始的写法
//         * 实现某个接口，在实现方法中写代码逻辑。所以重点4只是一个定义，重点2调用执行，千万不要误解。可以debug程序，你会发现其实到重点4并不会进入代码内部，直至运行到重点2，
//         * 触发，然后进入重点4内部执行代码*/
//        System.out.println(JSON.toJSONString(zhi));
//
//    }
//        public static void shuju(testjiekou jiekou,int start, int end){
//            jiekou.test(start,end);
//        }
}
