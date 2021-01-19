package com.prayerlaputa.beanpostprocessor.dynamicinjectinterface;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * @author chenglong.yu
 * created on 2021/1/18
 */
public class CustomBaseMapper implements BaseMapper {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private List<String> dataList = new CopyOnWriteArrayList<>();

    /**
     * @param value
     */
    @Override
    public void add(String value) {
        logger.info("添加数据:" + value);
        dataList.add(value);
    }

    /**
     * @param key
     */
    @Override
    public void remove(String key) {
        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("Can't remove because the list is Empty!");
        }

        Iterator<String> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            String cur = iterator.next();
            if (Objects.equals(key, cur)) {
                iterator.remove();
                break;
            }
        }
    }

}
