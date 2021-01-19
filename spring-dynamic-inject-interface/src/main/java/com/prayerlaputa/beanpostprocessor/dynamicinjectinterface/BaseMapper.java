package com.prayerlaputa.beanpostprocessor.dynamicinjectinterface;

/**
 * @author chenglong.yu
 * created on 2021/1/18
 */
public interface BaseMapper {

    /**
     * @param value
     */
    void add(String value);

    /**
     * @param key
     */
    void remove(String key);
}
