package com.prayerlaputa.beanpostprocessor.basic;

import com.prayerlaputa.beanpostprocessor.basic.annotation.RoutingInjected;

import com.prayerlaputa.beanpostprocessor.basic.service.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author chenglong.yu
 * created on 2021/1/16
 */
@Component
public class HelloServiceTest {


    @RoutingInjected(value = "helloServiceImpl2")
    private HelloService helloService;

    public void testSayHello() {
        this.helloService.sayHello();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.prayerlaputa.beanpostprocessor.basic");
        HelloServiceTest helloServiceTest = applicationContext.getBean(HelloServiceTest.class);
        helloServiceTest.testSayHello();
    }
}
