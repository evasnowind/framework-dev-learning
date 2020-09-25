package com.prayerlaputa.springsenior.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author chenglong.yu
 * created on 2020/9/25
 */
@Component
public class StudentAddListener implements ApplicationListener<StudentAddEvent> {


    @Override
    public void onApplicationEvent(StudentAddEvent event) {
        if (null == event) {
            System.out.println("onApplicationEvent() 传入参数为null!");
            return;
        }

        System.out.println("onApplicationEvent() handle,studentName=" + event.getStudentName());
    }
}
