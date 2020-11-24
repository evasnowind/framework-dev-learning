package com.prayerlaputa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * EnableAspectJAutoProxy注解两个参数作用分别为：
 *
 * 一个是控制aop的具体实现方式,为true的话使用cglib,为false的话使用java的Proxy，默认为false。
 * 第二个参数控制代理的暴露方式,解决内部调用不能使用代理的场景，默认为false。
 * 使用AopContext.currentProxy()获取对代理对象时需要开启exposeProxy = true
 *
 */
//@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
public class SpringAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
	}
}
