package com.prayerlaputa.controller;

import com.prayerlaputa.service.AnnotationDemoService;
import com.prayerlaputa.service.MyCacheDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenglong.yu
 * created on 2020/11/25
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class MyCacheProxyTestController {

    @Resource
    private AnnotationDemoService annotationFailDemoService;

    @GetMapping("/test/get")
    public String getValByCache(String key) {
        log.info("controller getValByCache param:{}.", key);
        return annotationFailDemoService.getValByCache(key);
    }

    @GetMapping("/test/v1")
    public String testAnnotation1(String key) {
        log.info("testAnnotation1 key={}.", key);
        return annotationFailDemoService.testAnnotation1(key);
    }

    @GetMapping("/test/v2")
    public String testAnnotation2(String key) {
        log.info("testAnnotation2 key={}.", key);
        return annotationFailDemoService.testAnnotation2(key);
    }

    @GetMapping("/test/v3")
    public String testAnnotation3(String key) {
        log.info("testAnnotation3 key={}.", key);
        return annotationFailDemoService.testAnnotation3(key);
    }


    @GetMapping("/test/v4")
    public String testAnnotation4(String key) {
        log.info("testAnnotation4 key={}.", key);
        return annotationFailDemoService.testAnnotation4(key);
    }

}
