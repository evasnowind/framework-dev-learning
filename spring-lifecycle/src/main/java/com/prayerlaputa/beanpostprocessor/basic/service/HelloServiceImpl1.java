package com.prayerlaputa.beanpostprocessor.basic.service;

import org.springframework.stereotype.Service;

/**
 * @author chenglong.yu
 * created on 2021/1/16
 */
@Service
public class HelloServiceImpl1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("你好我是HelloServiceImpl1");
    }
}
