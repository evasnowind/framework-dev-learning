package com.prayerlaputa.springaop.controller;

import com.prayerlaputa.springaop.constant.GlobalConst;
import com.prayerlaputa.springaop.core.CommonResp;
import com.prayerlaputa.springaop.core.Passport;
import com.prayerlaputa.springaop.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public CommonResp test() {
        Passport passport = new Passport();
        passport.setUserId(GlobalConst.DEFAULT_USER_ID);
        passport.setToken(GlobalConst.DEFAULT_TOKEN);
        return demoService.action0(passport, "666");
    }

    @RequestMapping("/test1")
    public CommonResp test1() {
        Passport passport = new Passport();
        passport.setUserId(101);
        passport.setToken("aaaaaa");
        return demoService.action0(passport, "777");
    }
}
