package com.prayerlaputa.beanpostprocessor.dynamicinjectinterface.test;

import com.prayerlaputa.beanpostprocessor.dynamicinjectinterface.test.dao.UserMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenglong.yu
 * created on 2021/1/18
 */
public class TestMapper {

    public static final String MAPPER_PACKAGE_INFO = "com.prayerlaputa.beanpostprocessor.dynamicinjectinterface.test.dao";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext acApplicationContext = new AnnotationConfigApplicationContext(MAPPER_PACKAGE_INFO);
        UserMapper userMapper = acApplicationContext.getBean(UserMapper.class);
        userMapper.add("test mapper");
        acApplicationContext.stop();
    }
}
