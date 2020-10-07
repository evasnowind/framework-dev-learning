package com.prayerlaputa.dynamicproxy.compareproxydemo;

import java.util.Date;

/**
 * @author chenglong.yu
 * created on 2020/10/9
 */
public class SimpleServiceImpl implements SimpleService {

    @Override
    public void consumer() {
        new Date();
    }
}
