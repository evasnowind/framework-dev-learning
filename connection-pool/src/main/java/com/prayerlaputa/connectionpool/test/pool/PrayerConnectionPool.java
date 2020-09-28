package com.prayerlaputa.connectionpool.test.pool;

import java.sql.Connection;

/**
 * @author chenglong.yu
 * created on 2020/9/1
 */
public interface PrayerConnectionPool {

    void releaseConnection(Connection connection);
    Connection fetchConnection(long mills) throws Exception;
}
