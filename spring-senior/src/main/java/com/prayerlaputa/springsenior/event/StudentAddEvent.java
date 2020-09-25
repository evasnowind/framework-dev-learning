package com.prayerlaputa.springsenior.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author chenglong.yu
 * created on 2020/9/25
 */
public class StudentAddEvent extends ApplicationEvent {

    private String studentName;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public StudentAddEvent(Object source, String studentName) {
        super(source);
        this.studentName = studentName;
    }

    public StudentAddEvent(Object source) {
        super(source);
    }

    public String getStudentName(){
        return studentName;
    }
}
