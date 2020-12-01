package com.prayerlaputa.datasource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义ReadOnly注解，只有加该注解才会去读从库。
 *
 * @author chenglong.yu
 * created on 2020/12/1
 */
@Aspect
@Component
@Slf4j
public class ReadOnlyAspect {


    @Pointcut("@annotation(com.prayerlaputa.datasource.ReadOnly)")
    public void readOnly() {

    }

    @Before("readOnly()")
    public void beforeDbOp(JoinPoint joinPoint) {
        if (hasMarkedReadOnly(joinPoint)) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            log.info("switch data source to [{}] in for method [{}].",
                    DynamicDataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
        }
    }

    @After("readOnly()")
    public void afterDbOp(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.info("restore data source to [{}] after method [{}].",
                DynamicDataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());

    }

    @SneakyThrows
    private boolean hasMarkedReadOnly(JoinPoint point) {

        Object[] args = point.getArgs();
        Class<?>[] argTypes = new Class[point.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ReadOnly readOnly = method.getAnnotation(ReadOnly.class);
        if (null != readOnly) {
            return true;
        } else {
            return false;
        }
    }
}
