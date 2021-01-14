# framework-dev-learning

This repo gives examples of different Java concept and programming technique that are frequently used in the development of frameworks. For instance, when we want to create an RPC framework from scratch, we need to know Java dynamic proxy and serialization. 

Here is the list: 

| Technique | module/link | scenario | status | remark |
| - | - | - | - | - |
|dynamic proxy | dynamic-proxy | RPC<br/>MQ | done |  |
|byte program<br/>java agent,<br/>instrument,<br/>byte buddy | bytecode-program<br/>java-agent-and-instrument<br/>java-agent-and-instrument-test<br/>[java monitor by java agent](https://github.com/evasnowind/distributed-dev-learning/tree/master/monitor-by-java-agent) | APM<br/>，tracking，<br/>method <br/>enhancement，<br/>hot module<br/>replacement | done | skywalking, <br/>pinpoint, zipkin；<br/>framework development<br/>，eg:dubbo、spring, <br/>arthas, jrebel and so on. |
|connection pool | connection-pool | Druid, C3P0, <br/>HikariCP | done | a simple connection pool:<br/>https://github.com/aloys-jun/connect-pool |
| Java JUC | https://github.com/evasnowind/JUC-learning |  | done |  |
| Java CAS | java-cas |  | done |  |
| Java SPI | java-spi | JDBC | done |  |
| Java Zero Copy | java-zero-copy | high performance <br/>IO，like kafka | done | |
| Dubbo SPI | | Dubbo | to do | |
| Spring event | spring-event |  | done | event notify |
| Spring AOP | spring-aop |  | done |  |
| Spring Factories,<br/>Spring Import,<br/>Spring Condition | spring-conditional-annotation<br/>spring-import<br/> |framework <br/>development|done| spring boot starter |
| spring-boot-starter<br/>                             | tiny-http-server-spring-boot-starter<br/>[a simple starter](https://github.com/evasnowind/tiny-school-spring-boot-starter) | implement a  <br/>spring boot starter | done |  |
| netty | netty-httpserver | network IO，<br/>eg: RocketMQ | done | rocketmq, dubbo, seata<br/> and so on. Kafka doesn't <br/>use netty，but use NIO。 |
| dynamic datasource<br/>separate read <br/>write | dynamic-datasource | db midware，<br/>separate read write，<br/>MyCAT,<br/>shardingsphere-jdbc | done | other ：[dynamic datasource](https://github.com/baomidou/dynamic-datasource-spring-boot-starter) |



## Support  

Supported by [Jetbrains IDEA](https://www.jetbrains.com/?from=IDEA)

![](docs/images/jetbrains.png)