package com.prayerlaputa.springsenior.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/9/25
 */
public class StudentAddBean {

    private ApplicationContext appContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public void addStudent(String studentName) {
        StudentAddEvent event = new StudentAddEvent(appContext, studentName);
        System.out.println(">>>publishEvent");
        appContext.publishEvent(event);
    }
}
