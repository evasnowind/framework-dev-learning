package com.prayerlaputa.beanpostprocessor.dynamicinjectinterface;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.logging.Logger;

/**
 * @author chenglong.yu
 * created on 2021/1/18
 */
public class BaseMapperFactoryBean<T> implements FactoryBean<T>, InitializingBean, ApplicationListener<ApplicationEvent>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * 要注入的接口类定义
     */
    private Class<T> mapperInterface;

    /**
     * Spring上下文
     */
    private ApplicationContext applicationContext;

    //也因该走工厂方法注入得来

    private BaseMapper mapperManagerFactoryBean;

    public BaseMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        //采用动态代理生成接口实现类，核心实现
        return (T) Proxy.newProxyInstance(applicationContext.getClassLoader(), new Class[]{mapperInterface}, new MapperJavaProxy(mapperManagerFactoryBean, mapperInterface));
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 判断属性的注入是否正确-如mapperInterface判空
        if (null == mapperInterface)
            throw new IllegalArgumentException("Mapper Interface Can't Be Null!!");
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //TODO 可依据事件进行扩展

        logger.info("收到事件：" + event);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }


    public void setMapperManagerFactoryBean(BaseMapper mapperManagerFactoryBean) {
        this.mapperManagerFactoryBean = mapperManagerFactoryBean;
    }

}
