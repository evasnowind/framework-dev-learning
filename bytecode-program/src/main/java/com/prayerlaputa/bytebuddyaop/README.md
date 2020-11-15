## 尝试使用ByteBuddy实现一个简单的基于类的AOP  

参考了多个资料，目前写了3个通过ByteBuddy实现AOP代理的demo。

### 方法注解，方法执行前后拦截  
- 代码：src/main/java/com/prayerlaputa/bytebuddyaop/methodaop1
- 测试：ByteBuddyAopDemo
- 代码说明：通过ByteBuddy自定义了MyLog注解，在MyService的doBusiness方法前后拦截

### 方法注解，方法执行结束时通过AOP输出方法入参、出参、耗时等  
- 代码：src/main/java/com/prayerlaputa/bytebuddyaop/methodaop2
- 测试：ByteBuddyAopDemo2
- 代码说明：通过ByteBuddy生成一个代理，将MyService2的doBusiness方法交给ByteBuddyMonitorDemo来执行，ByteBuddyMonitorDemo中的代理方法执行完业务方法后，还会输出方法入参、出参、耗时等信息。
 
### 类和方法注解，继承一个抽象类并实现抽象方法  
- 代码：src/main/java/com/prayerlaputa/bytebuddyaop/classandmethodaop
- 测试：ByteBuddyAopDemo3
- 代码说明：直接通过ByteBuddy生成一个类，该类继承抽象类Repository，并添加了自定义的两个注解，将抽象方法委托给UserRepositoryInterceptor来执行。


## 参考资料
- [字节码编程](https://bugstack.cn/itstack/itstack-demo-bytecode.html)