# BeanPostProcessor的基本使用  

本包的代码取自 [Spring实战系列（三）-BeanPostProcessor的妙用](https://blog.csdn.net/geekjoker/article/details/79868945)

## 说明

### 目标

同一个接口有多个实现类，在不同场景下需指定不同的实现类对象。

### 方式

- 方法1：直接通过ApplicationContext拿对象，可以抽象出一个工厂方法
   -  不够优雅
- 方法2：通过注解注入对象引用时，加个配置来指定将要使用哪个实现类。
   -  需要自定义BeanPostProcessor
### 程序说明

```text
├─basic
│  │  HelloServiceTest.java
│  │
│  ├─annotation //注解
│  │      RoutingInjected.java
│  │
│  ├─processor //通过自定义BeanPostProcessor的方式，在注入对象引用时，动态调整注入哪个实现类
│  │      //接口只有1个实现类，则直接注入；有多个实现类，则更新自定义注解RoutingInjected的值来注入所需实现类
|  | 	  HelloServiceBeanPostProcessor.java 
|  |	 //使用java反射、动态代理，生成一个proxy，proxy代理service接口，当需要获取bean时，该代理从spring容器根据传入名称拿对象
│  │      RoutingBeanProxyFactory.java
│  │
│  └─service //service接口以及该接口的2个不同的实现
│          HelloService.java
│          HelloServiceImpl1.java
│          HelloServiceImpl2.java
```

