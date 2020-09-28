package com.prayerlaputa.connectionpool.common;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author chenglong.yu
 * created on 2020/9/24
 */
public class TestConnectionPool {

    public static void main(String[] args) throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/seckill";
        String username = "root";
        String password = "";

        DbConfig dbConnectionConfig = new DbConfig(
                driver, url, username, password);
        DbConnectionFactory dbConnectionFactory = new DbConnectionFactory(dbConnectionConfig);

        DbPoolConfig dbPoolConfig = new DbPoolConfig();
        dbPoolConfig.setMaxTotal(10);
        dbPoolConfig.setMaxIdle(5);
        dbPoolConfig.setMinIdle(2);
        dbPoolConfig.setMaxWaitMillis(200);
        dbPoolConfig.setTestOnBorrow(false);
        dbPoolConfig.setTestOnReturn(false);

        DbConnectionPool dbPool = new DbConnectionPool(dbPoolConfig, dbConnectionFactory);

        for (int i = 5; i < 20; i++) {
            Connection connection = dbPool.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("insert into miaosha_goods(goods_id, miaosha_price, stock_count, start_date, end_date) value(22, 2.33, 2233, '2020-09-01', '2020-09-02')");
            dbPool.returnConnection(connection);
        }
    }
}
