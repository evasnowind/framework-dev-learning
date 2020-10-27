package com.prayerlaputa.springsenior;

import com.prayerlaputa.springsenior.event.NotifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chenglong.yu
 * created on 2020/9/27
 */
@SpringBootApplication
@Slf4j
public class EventApplication {

    public static void main(String[] args) {

        log.info("EventApplication 服务开始启动...");
        ApplicationContext context = SpringApplication.run(EventApplication.class, args);
        NotifyEvent event = new NotifyEvent(context, "abc@qq.com", "This is the content");
        context.publishEvent(event);
        log.info("EventApplication 服务启动完成.");
    }

}
