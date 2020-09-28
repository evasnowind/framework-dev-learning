package com.prayerlaputa.springsenior.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author chenglong.yu
 * created on 2020/9/27
 */
public class NotifyEvent extends ApplicationEvent {


    private String email;

    private String content;

    /**
     * Create a new ContextRefreshedEvent.
     *
     * @param source the {@code ApplicationContext} that has been initialized
     *               or refreshed (must not be {@code null})
     */
    public NotifyEvent(Object source) {
        super(source);
    }

    public NotifyEvent(Object source, String email, String content) {
        super(source);
        this.content = content;
        this.email = email;
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
