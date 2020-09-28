package com.prayerlaputa.connectionpool.test.pool;


import com.prayerlaputa.connectionpool.test.config.PropertiesHolder;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author chenglong.yu
 * created on 2020/9/1
 */
public class PrayerConnectionPoolImpl implements PrayerConnectionPool {


    private LinkedList<Connection> pool = new LinkedList<>();


    //定义数据库连接属性
    private final static String DRIVER_CLASS = PropertiesHolder.getInstance().getProperty("jdbc.driver_class");
    private final static String URL = PropertiesHolder.getInstance().getProperty("jdbc.url");
    private final static String USERNAME = PropertiesHolder.getInstance().getProperty("jdbc.username");
    private final static String PASSWORD = PropertiesHolder.getInstance().getProperty("jdbc.password");

    //定义默认连接池属性配置
    private int initSize = 2;
    private int maxSize = 4;
    private int stepSize = 1;
    private int timeout = 2000;
    private int createdConnCnt = 0;

    public PrayerConnectionPoolImpl() {
        initPool();
    }

    private void initPool() {
        String init = PropertiesHolder.getInstance().getProperty("initSize");
        String step = PropertiesHolder.getInstance().getProperty("stepSize");
        String max = PropertiesHolder.getInstance().getProperty("maxSize");
        String time = PropertiesHolder.getInstance().getProperty("timeout");

        initSize = init == null ? initSize : Integer.parseInt(init);
        maxSize = max == null ? maxSize : Integer.parseInt(max);
        stepSize = step == null ? stepSize : Integer.parseInt(step);
        timeout = time == null ? timeout : Integer.parseInt(time);

        try {
            //加载驱动
            Driver driver = (Driver) Class.forName(DRIVER_CLASS).newInstance();
            //使用DriverManager注册驱动
            DriverManager.registerDriver(driver);

            //预先创建数据库连接放入到连接池中
            createConnection(initSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        if (null != connection) {
            synchronized (pool) {
                //连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                //一般调用notifyAll
                pool.notifyAll();
                System.out.println(Thread.currentThread().getName() + "释放了连接");
            }
        }
    }

    @Override
    public Connection fetchConnection(long mills) throws Exception {
        synchronized (pool) {
            if (mills <= 0) {
                if (pool.isEmpty()) {
                    /*
                    实际上此处实现有些问题，当连接池中的连接都被取走、再一次调用fetchConnection时，会创建新的连接。
                    当这些连接都释放、返回给pool时，数量将可能超过maxSize。
                    解决方法也很简单，使用一个变量记录已经提供了多少线程、创建了多少线程。如果超过maxSize，应该抛出异常或是阻塞？看需求场景。
                    ——本程序只是个demo，就不写那么多了，判断略麻烦
                     */
                    createConnection(initSize);
                }
                //池中没有连接，需要阻塞等待
                while (pool.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + "当前被阻塞");
                    pool.wait();
                }
                //阻塞结束，返回连接
                System.out.println(Thread.currentThread().getName() + "获取了连接对象");
                return pool.removeFirst();
            } else {
                //获取数据库连接时设置了超时时间
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    System.out.println(Thread.currentThread().getName() + "当前被阻塞");
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                /*
                设置超时时间情况下，如果过了时间还拿不到连接，不阻塞、直接返回
                这里就要看对于连接池的需求/期望是怎样的，根据需求来实现不同功能。
                 */
                Connection result = null;
                if (!pool.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + "获取了连接对象");
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    private void createConnection(int count) throws SQLException {
        if (createdConnCnt + count < maxSize) {
            //初始数量少于maxSize，直接创建count个连接
            for (int i = 0; i < count; i++) {
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                pool.addLast(conn);
            }
            createdConnCnt += count;
        } else if (createdConnCnt < maxSize) {
            /*
            createdConnCnt + count >= maxSize，但 createdConnCnt<maxSize
            说明池子创建的连接数量少于maxSize，还可以继续创建
             */
            for (int i = createdConnCnt; i < maxSize; i++) {
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                pool.addLast(conn);
            }
            createdConnCnt = maxSize;
        } //else 池子的连接数已满，不能再创建新的连接
    }
}
