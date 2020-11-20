package com.prayerlaputa.controller;

import com.prayerlaputa.service.MyCacheDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenglong.yu
 * created on 2020/11/20
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class MyCacheDemoController {

    @Autowired
    private MyCacheDemoService myCacheDemoService;

    @GetMapping("/get/val")
    public String getValByCache(String key) {
        log.info("controller getValByCache param:{}.", key);
        return myCacheDemoService.getValByCache(key);
    }
}
