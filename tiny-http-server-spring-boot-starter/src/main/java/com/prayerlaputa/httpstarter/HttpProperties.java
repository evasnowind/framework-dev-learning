package com.prayerlaputa.httpstarter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenglong.yu
 * created on 2020/11/15
 */
@ConfigurationProperties(prefix = "http")
@Getter
@Setter
public class HttpProperties {

    // 如果配置了属性，则该属性会被覆盖
    private String url = "http://www.baidu.com/";

    // @Value 适合用于某一个特定的参数
    // 而properties适合于**某一类**的参数
    // 而且properties一般需要配合autoConfig使用，而value注解则一般是单独使用

    // 属性的获取和对象的创建是完全分开的

    // 一旦被加入依赖，则类就被加入classpath，这将使得注解可以被扫描到
    // 所以只要加入依赖就可以开始工作
}
