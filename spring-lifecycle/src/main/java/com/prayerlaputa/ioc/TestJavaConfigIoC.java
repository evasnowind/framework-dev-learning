package com.prayerlaputa.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/11/24
 */
public class TestJavaConfigIoC {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        Person person = (Person)context.getBean("person");
        System.out.println(person.getAnimal().getName());
    }
}
