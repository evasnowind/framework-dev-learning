package com.prayerlaputa.springevent.controller;

import com.prayerlaputa.springevent.event.NotifyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenglong.yu
 * created on 2020/9/28
 */
@RestController
@RequestMapping("/api/test")
public class EventController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/event")
    public String testEvent() {
        NotifyEvent event = new NotifyEvent(context, "abc@qq.com", "This is controller notify event test.");
        context.publishEvent(event);
        return "hello";
    }
}
