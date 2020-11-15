package com.prayerlaputa.assemblebean.autoinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
@Configuration
public class BeanConfig {
    @Bean
    public Person person(){
        return new Person();
    }

    @Bean
    public Cat cat(){
        return new Cat();
    }

    @Bean
    //创建这个bean的必要条件,matches方法必须为true才会注册bean，反之忽略bean。
    //扫描到bean立刻调用，不会等配置类中的所有bean都注册好之后再调用，因此Cat bean写前面，写后面会出错。
    @Conditional({PetCondition.class})
    public Dog dog(){
        return new Dog();
    }

}
