package com.prayerlaputa.service;

import com.prayerlaputa.annotation.MyCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenglong.yu
 * created on 2020/11/25
 */
@Service
@Slf4j
public class AnnotationDemoService  implements ApplicationContextAware {

    private AtomicInteger idx = new AtomicInteger();

    @MyCache(50)
    public String getValByCache(String key) {
        log.info("getValByCache running...");
        return key + "-" + idx.incrementAndGet();
    }

    public String testAnnotation1(String key) {
        return this.getValByCache(key);
    }


    @Autowired
    private MyCacheDemoService self;

    public String testAnnotation2(String key) {
        return self.getValByCache(key);
    }

    public String testAnnotation3(String key) {
        return ((AnnotationDemoService)AopContext.currentProxy()).getValByCache(key);
    }



    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String testAnnotation4(String key) {
        return applicationContext.getBean(AnnotationDemoService.class).getValByCache(key);
    }
}
