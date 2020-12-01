package com.prayerlaputa.config;

import com.prayerlaputa.datasource.DataSourceKey;
import com.prayerlaputa.datasource.DynamicDataSourceContextHolder;
import com.prayerlaputa.datasource.DynamicRoutingDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenglong.yu
 * created on 2020/12/1
 */
@Configuration
public class DatasourceConfig {


    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }


    @Bean("slave0")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave0")
    public DataSource slave0() {
        return DataSourceBuilder.create().build();
    }

    /**
     * TODO 修改实例化逻辑，尽量通过配置文件来控制生成多少个slave，而不是硬编码
     *
     * @return
     */
    @Bean("slave1")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave1")
    public DataSource slave1() {
        return DataSourceBuilder.create().build();
    }


    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(DataSourceKey.master.name(), master());
        dataSourceMap.put(DataSourceKey.slave0.name(), slave0());
        dataSourceMap.put(DataSourceKey.slave1.name(), slave1());

        //
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        Set<Object> keySet = dataSourceMap.keySet();
        DynamicDataSourceContextHolder.addDataSourceKeys(keySet);

        keySet.remove(DataSourceKey.master.name());
        DynamicDataSourceContextHolder.addSlaveDataSourceKeys(keySet);

        return dynamicRoutingDataSource;
    }


    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Here to config mybatis
        sqlSessionFactoryBean.setTypeAliasesPackage("com.prayerlaputa.mapper");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mappers/**Mapper.xml"));
        // Here is very important, if don't config this, will can't switch datasource
        // put all datasource into SqlSessionFactoryBean, then will autoconfig SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
