package com.prayerlaputa.connectionpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author chenglong.yu
 * created on 2020/9/24
 */
public class DbConnectionFactory  extends BasePooledObjectFactory<Connection> {

    private DbConfig dbConfig;

    public DbConnectionFactory(DbConfig dbConfig){
        super();
        this.dbConfig = dbConfig;
    }

    /**
     * 创建新的数据库连接
     *
     * @return
     * @throws Exception
     */
    @Override
    public Connection create() throws Exception {
        Connection connection = DriverManager.getConnection(dbConfig.getUrl(),
                dbConfig.getUsername(), dbConfig.getPassword());
        return connection;
    }

    /**
     * 使用PooledObject对数据库连接进行包装
     */
    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject(connection);
    }

    /**
     * 由于validateObject失败或其它什么原因，对象实例从对象池中移除时调用
     * 不能保证对象实例被移除时所处的状态
     * @param pooledConnection
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Connection> pooledConnection) throws Exception {
        Connection connection = pooledConnection.getObject();
        connection.close();
    }

    /**
     * 仅能被active状态的对象实例调用
     * 从对象池获取对象实例，在对象池返回该对象实例前，调用该方法校验其状态
     * 对象实例归还给对象池时，在调用passivateObject方法前，使用该方法校验其状态
     *
     * @param pooledConnection
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Connection> pooledConnection) {
        Connection connection = pooledConnection.getObject();
        try {
            if (connection.isValid(1)) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 对象实例在归还给对象池时调用了passivateObject方法，通过对象池再次取到该对象实例
     * 在对象池返回该对象实例前，需要调用该方法
     * @param pooledConnection
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<Connection> pooledConnection) throws Exception {

    }

    /**
     * 对象实例归还给对象池时调用
     */
    @Override
    public void passivateObject(PooledObject<Connection> pooledConnection) throws Exception {

    }

}
