package com.prayerlaputa.connectionpool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author chenglong.yu
 * created on 2020/9/24
 */
public class DbPoolConfig extends GenericObjectPoolConfig {

    public DbPoolConfig(){
        setMaxTotal(10);
        setMaxIdle(5);
        setMinIdle(2);
    }
}
