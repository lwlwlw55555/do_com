package com.domdd.ioc;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author lw
 * @date 2022/5/6 4:57 下午
 */
@Component
public class NotifyListener implements ApplicationListener<NotifyEvent> {

    @Override
    public void onApplicationEvent(NotifyEvent event) {
        System.out.println("邮件地址：" + event.getEmail());
        System.out.println("邮件内容：" + event.getContent());
    }
}

