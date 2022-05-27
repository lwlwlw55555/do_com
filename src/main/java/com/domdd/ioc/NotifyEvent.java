package com.domdd.ioc;

import org.springframework.context.ApplicationEvent;

/**
 * @author lw
 * @date 2022/5/6 4:57 下午
 */
public class NotifyEvent extends ApplicationEvent {


    private String email;

    private String content;

    public NotifyEvent(Object source) {
        super(source);
    }

    public NotifyEvent(Object source, String email, String content) {
        super(source);
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
