package com.domdd.service.proxy;

import com.alibaba.fastjson.JSON;
import com.domdd.DoMddApplication;
import com.domdd.proxy.jdk.DoProxyFactory;
import com.domdd.proxy.jdk.DoProxyInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lw
 * @date 2022/3/18 4:50 下午
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoProxyTest {

    @Test
    public void test01() {
//        InvocationHandler h = new DoProxy();

//        DoProxy o = (DoProxy) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{DoProxyInterface.class}, h);
        //这里生成了一个实现了DoProxyInterface的且继承了Proxy的类，这就是jdk动态代理...
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        DoProxyInterface doProxy = DoProxyFactory.newInstance(DoProxyInterface.class);
        System.out.println(doProxy.test01());

        String s = doProxy.test02("lw", "test");
        System.out.println(s);
        System.out.println(JSON.toJSONString(doProxy));

    }

    @Test
    public void test02() {

    }

//    com.baomidou.mybatisplus.core.override.MybatisMapperProxy@4298e4b4

}
