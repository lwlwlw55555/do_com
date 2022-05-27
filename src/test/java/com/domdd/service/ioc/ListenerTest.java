package com.domdd.service.ioc;

import com.domdd.DoMddApplication;
import com.domdd.ioc.NotifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author lw
 * @date 2022/3/18 4:50 下午
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DoMddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListenerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testListener() {

        NotifyEvent event = new NotifyEvent("object", "abc@qq.com", "This is the content");

        webApplicationContext.publishEvent(event);

//        ApplicationListener applicationListener = (event1) -> {
//            System.out.println("1");
//        };
    }
}
