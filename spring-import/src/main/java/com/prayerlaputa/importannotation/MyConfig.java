package com.prayerlaputa.importannotation;

import org.springframework.context.annotation.Bean;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
public class MyConfig {


    @Bean
    public Dog getDog(){
        return new Dog();
    }

    @Bean
    public Cat getCat(){
        return new Cat();
    }

}
