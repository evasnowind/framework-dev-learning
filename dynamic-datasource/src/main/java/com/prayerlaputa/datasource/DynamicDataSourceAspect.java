package com.prayerlaputa.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * get query开头的方法自动使用从库去读，
 * 但无法避免“写完读”不一致的问题。
 *
 * @author chenglong.yu
 * created on 2020/12/1
 */
//@Component
@Aspect
@Slf4j
public class DynamicDataSourceAspect {

    private static final String[] QUERY_PREFIX = new String[]{"get", "query"};


    @Pointcut("execution( * com.prayerlaputa.mapper.*.*(..))")
    public void daoAspect() {

    }

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint jointPoint) {
        if (isQueryMethod(jointPoint.getSignature().getName())) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            log.info("switch data source to [{}] in for method [{}].",
                    DynamicDataSourceContextHolder.getDataSourceKey(), jointPoint.getSignature());
        }
    }

    @After("daoAspect()")
    public void restoreDataSource(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.info("restore data source to [{}] after method [{}].",
                DynamicDataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
    }

    private boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
