
## 有关动态代理   

代理模式常见的实现有两种，静态代理和动态代理。

**静态代理**：编译时增强，AOP 框架会在编译阶段生成 AOP 代理类，在程序运行前代理类的.class文件就已经存在了。常见的实现：JDK静态代理，AspectJ 。

**动态代理**：运行时增强，它不修改代理类的字节码，而是在程序运行时，运用反射机制，在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。常见的实现：JDK、CGLIB、Javassist(Hibernate中使用的动态代理)


不过Spring AOP的实现没有用到静态代理，而是采用了动态代理的方式，有两种，JDK动态代理和CGLIB（Code Generator Library）动态代理。下面简述二者的差异。

- 在代理对象不是接口类型或不是代理类时，指定proxyTargetClass=true后，执行CGLIB代理
- 代理对象是接口类型或是代理类，使用JDK代理

## JDK动态代理和CGLIB（Code Generator Library）动态代理

### 实现原理
- JDK动态代理：基于反射，生成实现代理对象接口的匿名类，通过生成代理实例时传递的InvocationHandler处理程序实现方法增强。
- CGLIB动态代理：基于操作字节码，通过加载代理对象的类字节码，为代理对象创建一个子类，并在子类中拦截父类方法并织入方法增强逻辑。底层是依靠ASM（开源的java字节码编辑类库）操作字节码实现的。



### 各自特点  
JDK动态代理特点：
- 代理对象必须实现一个或多个接口
- 以接口形式接收代理实例，而不是代理类

CGLIB动态代理特点：
- 代理对象不能被final修饰
- 以类或接口形式接收代理实例

### 性能比较  
JDK与CGLIB动态代理的性能比较：
网上据说新版本JDK动态代理性能有提升，这个得实际测试一下，比如JMH等，排除预热等干扰因素。

可以参考这个 https://blog.csdn.net/xlgen157387/article/details/82497594

## 参考资料  
- https://blog.csdn.net/luanlouis/article/details/24589193
- https://blog.csdn.net/wangzhihao1994/article/details/80913210