package com.prayerlaputa.springsenior.event;

import com.prayerlaputa.springevent.EventApplication;
import com.prayerlaputa.springevent.event.NotifyEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/9/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventApplication.class)
public class EventListenerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testListener() {

        NotifyEvent event = new NotifyEvent("object", "abc@qq.com", "This is the content");
        webApplicationContext.publishEvent(event);
    }
}
