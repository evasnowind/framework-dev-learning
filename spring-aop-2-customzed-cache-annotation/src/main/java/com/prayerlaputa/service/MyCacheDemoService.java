package com.prayerlaputa.service;

import com.prayerlaputa.annotation.MyCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenglong.yu
 * created on 2020/11/20
 */
@Slf4j
@Service
public class MyCacheDemoService {

    private AtomicInteger idx = new AtomicInteger();


//    @MyCache(expireTime = 40)
    @MyCache(50)
//    @MyCache(expireTime = 40, value = 50)
    public String getValByCache(String key) {
        log.info("getValByCache running...");
        return key + "-" + idx.incrementAndGet();
    }
}
