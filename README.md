# framework-dev-learning

本项目主要给出独立开发框架（如MQ/RPC等）时所需用到的核心技术。

常见技术列表：

| 技术 | module/地址 | 应用场景 | 状态 | 其他 |
| - | - | - | - | - |
|动态代理 | dynamic-proxy | RPC<br/>MQ | doing |  |
|字节码编程<br/>java agent,<br/>instrument,<br/>byte buddy | bytecode-program<br/>java-agent-and-instrument<br/>java-agent-and-instrument-test<br/>[一个简单starter实现](https://github.com/evasnowind/distributed-dev-learning/tree/master/monitor-by-java-agent) | 非侵入式应用监控<br/>，应用埋点，<br/>方法增强，<br/>热替换等 | done | 应用监控，如skywalking, <br/>pinpoint, zipkin等；<br/>框架开发，如dubbo、spring等；<br/>其他，如arthas, jrebel等 |
| |  |  |  | |
|连接池 | connection-pool | Druid等 | done | github上一个自定义实现的连接池：<br/>https://github.com/aloys-jun/connect-pool |
| Java JUC | https://github.com/evasnowind/JUC-learning |  | done |  |
| Java CAS | java-cas |  | done |  |
| Java SPI | java-spi | JDBC | done |  |
| Java Zero Copy | java-zero-copy | 高性能IO，如kafka | done | |
| Dubbo SPI | | Dubbo | to do | |
| Spring高级应用 | spring-senior |  | done | 事件通知机制：比如载入某个实例完成后<br/>通过该机制告知相关类继续往下走 |
| Spring AOP | spring-aop |  | done |  |
| Spring Factories,<br/>原理与使用，<br/>Spring Import,<br/>Spring Condition | spring-conditional-annotation<br/>spring-import<br/> |框架开发|done| 利用spring开发框架，<br/>第三方刻苦的开发，<br/>解决客户端的集成问题 |
| spring-boot-starter<br/>实现原理                             | tiny-http-server-spring-boot-starter<br/>[一个简单starter实现](https://github.com/evasnowind/tiny-school-spring-boot-starter) | 自定义<br/>spring boot starter | done | 开发一个框架时，自定义实现<br/>一个starter，便于作为第三方<br/>库，将客户端集成其他应用中 |
| netty | netty-httpserver | 高性能网络IO，<br/>如RocketMQ | done | rocketmq, dubbo, seata<br/>等。kafka没用netty，<br/>但也用了NIO。 |



