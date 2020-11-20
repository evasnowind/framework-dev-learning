package com.prayerlaputa.advice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.prayerlaputa.annotation.MyCache;
import com.prayerlaputa.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong.yu
 * created on 2020/11/20
 */
@Aspect
@Component
@Slf4j
public class MyCacheAdvice {


    /**
     * 使用google guava的缓存作为基础，复用其功能，比如说可以进一步优化，将cache可容纳的
     * 元素数量做成可配置。
     */
    private Cache localCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build();

    private DelayQueue<CacheTimeItem<String>> cacheTimeQueue = new DelayQueue<>();

    /**
     * 定期清理延迟队列，删除到期的
     */
    private ScheduledExecutorService scheduledRemoveService = Executors
            .newScheduledThreadPool(1, new NamedThreadFactory("schedule-remove"));

    /**
     * 定义调度任务，目前先写死，300ms执行一次，来检查延迟队列中是否有数据已过期。
     *
     * 测试时发现一个问题：如果设置定时调度时间间隔为200ms这样比较短的时间，可能出现还没移除完、就再次执行调度任务的情况。参见如下日志：
     * 2020-11-20 15:44:57.342  INFO 25820 --- [remove-thread-1] com.prayerlaputa.advice.MyCacheAdvice    : @MyCache find expire cache: key=aaa, cur cache size=0, queue size=1.
     * 2020-11-20 15:45:43.177  INFO 25820 --- [remove-thread-1] com.prayerlaputa.advice.MyCacheAdvice    : @MyCache find expire cache: key=aaa, cur cache size=0, queue size=0.
     *
     *
     */ {
        scheduledRemoveService.schedule((Runnable) () -> {
            while (true) {
                try {
                    CacheTimeItem<String> item = cacheTimeQueue.poll();
                    if (null != item) {
                        removeFromCache(item.getCacheKey());
                        log.info("@MyCache find expire cache: key={}, cur cache size={}, queue size={}.",
                                item.getCacheKey(), localCache.size(), cacheTimeQueue.size());
                    }
                } catch (Exception e) {
                    log.error("scheduledRemoveService error:", e);
                }
            }
        }, 200, TimeUnit.MILLISECONDS);
    }


    /**
     * 声明任意一个切入点：所有点有MyCache注解的任意方法
     */
    @Pointcut("@annotation(com.prayerlaputa.annotation.MyCache)")
    public void myCache() {

    }

    private static final String MY_CACHE_ANNOTATION_EXPIRE_TIME = "expireTime";

    @Around("myCache()")
    public Object around(ProceedingJoinPoint pjp) {
        Object value = null;
        try {
            MethodSignature methodSignature = getMethodSignatureByJoinPoint(pjp);
            //获取方法对象
            Method curMethod = getMethodByJoinPoint(pjp, methodSignature);
            String keyName = getKeyNameByMethodSignature(methodSignature);
            String keyArg = (String) pjp.getArgs()[0];

            /*
            @AliasFor是spring的注解，使用jdk的接口curMethod.getAnnotation(MyCache.class)获取到的值
            就无法拿到@MyCache(40)里面的值。必须使用spring的接口AnnotationUtils.getAnnotation获取annotation

            Annotation annotation = curMethod.getAnnotation(MyCache.class);
            Integer expireTime = (Integer) getAnnotationConfig(annotation, MY_CACHE_ANNOTATION_EXPIRE_TIME);
            Integer annotationValue = (Integer) getAnnotationConfig(annotation, "value");
            log.info("annotationValue={}, expireTime={}.", annotationValue, expireTime);
             */
            Annotation annotation = AnnotationUtils.getAnnotation(curMethod, MyCache.class);
            Integer expireTime = (Integer) getAnnotationConfig(annotation, MY_CACHE_ANNOTATION_EXPIRE_TIME);
            log.info("{}={}, expireTime={}.", keyName, keyArg, expireTime);

            /*
            核心逻辑1：
            从本地缓存中先查找：
            若缓存中有：
                则刷新其过期时间，然后直接返回结果；
            若缓存中没有：
                才真正执行业务方法，获得返回值；
                加入缓存，
                再返回
             */
            value = localCache.getIfPresent(keyArg);
            if (null != value) {
                log.info("@MyCache(key={}) local cache hint!", keyArg);
                refreshCache(keyArg, expireTime);
            } else {
                log.info("@MyCache add new item:key={}, expireTime config={}.", keyArg, expireTime);
                value = pjp.proceed();
                addToCache(keyArg, value, expireTime);
            }
        } catch (Throwable e) {
            log.error("around error:", e);
        }

        return value;
    }


    /**
     * 刷新延迟队列
     *
     * @param key
     * @param expireTime 还有多久过期，单位为秒
     */
    private void refreshCache(String key, int expireTime) {
        //刷新过期时间
        cacheTimeQueue.remove(key);
        this.addToTimeQueue(key, expireTime);
    }

    /**
     * 加入延迟队列，传入参数expireTime表示还有多久过期，单位为秒，
     * 该方法将会把expireTime转成毫秒、然后再加入延迟队列。
     *
     * @param key
     * @param expireTime 还有多久过期，单位为秒
     */
    private void addToTimeQueue(String key, int expireTime) {
        long expireAt = System.currentTimeMillis() + expireTime * 1000;
        cacheTimeQueue.add(new CacheTimeItem(expireAt, key));
    }

    private void addToCache(String key, Object value, int expireTime) {
        this.localCache.put(key, value);
        this.addToTimeQueue(key, expireTime);
    }

    private void removeFromCache(String key) {
        this.localCache.invalidate(key);
    }

    private MethodSignature getMethodSignatureByJoinPoint(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用在方法上！");
        }

        return (MethodSignature) signature;
    }

    private String getKeyNameByMethodSignature(MethodSignature methodSignature) {
        String[] paramNames = methodSignature.getParameterNames();
        if (null == paramNames || paramNames.length > 1) {
            throw new IllegalArgumentException("传入参数个数必须是1个！");
        }

        return paramNames[0];
    }

    private Method getMethodByJoinPoint(ProceedingJoinPoint pjp, MethodSignature methodSignature) throws NoSuchMethodException {
        Object target = pjp.getTarget();
        return target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Object getAnnotationConfig(Annotation annotation, String name) {
        if (null == annotation || StringUtils.isEmpty(name)) {
            return null;
        }

        try {
            return annotation.annotationType().getMethod(name).invoke(annotation);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("getAnnotationConfig error:", e);
        }

        return null;
    }
}

class CacheTimeItem<T> implements Delayed {

    /* 触发时间, 单位为毫秒*/
    private long time;
    private T cacheKey;

    public long getTime() {
        return time;
    }

    public T getCacheKey() {
        return cacheKey;
    }

    public CacheTimeItem(long time, T cacheKey) {
        this.time = time;
        this.cacheKey = cacheKey;
    }

    @Override
    public boolean equals(Object obj) {
        CacheTimeItem item = (CacheTimeItem) obj;
        return this.cacheKey.equals(item.cacheKey);
    }

    @Override
    public int hashCode() {
        return cacheKey.hashCode();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (null == o) {
            return 1;
        }
        if (o == this) {
            return 0;
        }
        if (o instanceof CacheTimeItem) {
            CacheTimeItem item = (CacheTimeItem) o;
            if (time > item.getTime()) {
                return 1;
            } else if (time == item.getTime()) {
                return 0;
            } else {
                return -1;
            }
        }

        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }
}
