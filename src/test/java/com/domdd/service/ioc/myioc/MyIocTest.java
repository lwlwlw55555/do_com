package com.domdd.service.ioc.myioc;

import com.domdd.DoMddApplication;
import com.domdd.ioc.NotifyEvent;
import com.domdd.ioc.myioc.service.MyAnnotationConfigApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author lw
 * @date 2022/5/27 2:30 下午
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyIocTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testMyIoc() {
        MyAnnotationConfigApplicationContext applicationContext = new
                MyAnnotationConfigApplicationContext("com.domdd.ioc.myioc.dto");
        Object persion = applicationContext.getBean("persion");
        System.out.println(persion);
        //Account account= (Account) applicationContext.getBean("Account");
    }
}
