package com.prayerlaputa.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenglong.yu
 * created on 2020/12/1
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    /**
     * 通过维护一个计数器，实现对于从库的RR负载均衡策略
     */
    private static AtomicInteger counter = new AtomicInteger(0);
    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.master::name);

    private static List<Object> dataSourceKeys = new ArrayList<>();

    private static List<Object> slaveDataSourceKeys = new ArrayList<>();

    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    public static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DataSourceKey.master.name());
    }

    public static void useSlaveDataSource() {
        try {
            //负载均衡
            int dataSourceKeyIndex = counter.getAndIncrement() % slaveDataSourceKeys.size();
            CONTEXT_HOLDER.set((String) slaveDataSourceKeys.get(dataSourceKeyIndex));
        } catch (Exception e) {
            log.error("switch slave datasouce failed, error:", e);
            useMasterDataSource();
        }
    }

    public static void addDataSourceKeys(Collection keys) {
        dataSourceKeys.addAll(keys);
    }

    public static void addSlaveDataSourceKeys(Collection keys) {
        slaveDataSourceKeys.addAll(keys);
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}
