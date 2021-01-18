package com.prayerlaputa.beanpostprocessor.senior;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author chenglong.yu
 * created on 2021/1/18
 */
public class MapperManagerFactoryBean implements FactoryBean<BaseMapper>, InitializingBean, ApplicationListener<ApplicationEvent> {

    @Override
    public BaseMapper getObject() throws Exception {
        return new CustomBaseMapper();
    }

    @Override
    public Class<?> getObjectType() {
        return BaseMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 判断属性的注入是否正确
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }

}
