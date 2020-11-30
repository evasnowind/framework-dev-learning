package com.prayerlaputa.batchinsert;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chenglong.yu
 * created on 2020/11/30
 */
public class MyHikariPool {

    private static HikariDataSource ds;


    public static void createDataSource(String url, String username, String password, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");

        config.setMaximumPoolSize(poolSize <= 0 ? 10 : poolSize);
        config.setValidationTimeout(3000);
        config.setConnectionTimeout(60000);
        ds = new HikariDataSource(config);
    }

    public static Connection getConn() throws SQLException {
        return null == ds ? null : ds.getConnection();
    }

    public static int getMaxPoolSize() {
        return ds.getMaximumPoolSize();
    }

    public static void closeConnection() {
        if (null != ds) {
            ds.close();
        }
    }
}
