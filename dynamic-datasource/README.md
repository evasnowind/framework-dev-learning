# 动态切换数据源版本



本项目手工实现了数据库主从分离后，动态切换数据源，读数据可以从主库、从库读取，写数据则只能从主库写。主从数据库需要配置主从复制。这个没有在本项目中展示出来。

为了方便演示，本项目中配置了同一个mysql示例、不同数据库，用于区分主库、从库。数据库文件参见`resources/db/schema.sql`    

## 目前已实现  

- 读写分离，动态切换数据源
- 支持多个从库的负载均衡
- 支持配置多个从库
    - 算是实现一半吧，因为我觉得最好的效果是只需要配好配置文件就多个配好多个从库，目前的话实现是在@Configuration配置类里写死了3个库、每个库的配置从配置文件读取。具体参见`com.prayerlaputa.config.DatasourceConfig`。主要是时间不过用，哎。


## 测试验证  

1. 导入mysql数据库，修改数据库连接信息（mysql地址、端口、账号、密码等）  
2. 启动DynamicDatasourceApplication  
3. 访问本地接口测试  
    - 3.1 读从库：http://localhost:18081/product/1
        - 接口返回：{"id":1,"name":"slave0","price":1.0}
        - 后端程序输出如下日志，说明数据源先切换到了从库，又切回了主库：
        ```text
            2020-12-01 20:05:05.148  INFO 12940 --- [io-18081-exec-3] c.p.datasource.ReadOnlyAspect            : switch data source to [slave0] in for method [Product com.prayerlaputa.service.ProductService.select(long)].
            2020-12-01 20:05:05.150  INFO 12940 --- [io-18081-exec-3] c.p.datasource.DynamicRoutingDataSource  : current datasource=slave0.
            2020-12-01 20:05:05.150  INFO 12940 --- [io-18081-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Starting...
            2020-12-01 20:05:05.161  INFO 12940 --- [io-18081-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Start completed.
            2020-12-01 20:05:05.173  INFO 12940 --- [io-18081-exec-3] c.p.datasource.ReadOnlyAspect            : restore data source to [master] after method [Product com.prayerlaputa.service.ProductService.select(long)].
        ```
    - 3.2 写主库：http://localhost:18081/product/add?name=aaa&price=2.3
        - 接口返回：true
        - 后端程序输出如下日志，说明数据源一直是主库：
        ```text
              2020-12-01 20:07:36.248  INFO 12940 --- [io-18081-exec-6] c.p.datasource.DynamicRoutingDataSource  : current datasource=master.
        ```
     

## 实现思路  

### 数据源的动态切换  

数据源动态切换的实现集中在 `com.prayerlaputa.datasource`包中：

#### DynamicRoutingDataSource  

继承了Spring AbstractRoutingDataSource，实现determineCurrentLookupKey方法。

#### DynamicDataSourceContextHolder  

保存主、从数据源信息，并封装了切换数据源的操作。  
当程序发生方法调用时，通过AOP在方法调用前，判断是读还是写操作，然后调用DynamicDataSourceContextHolder切换数据源。    
为了保证线程安全，DynamicDataSourceContextHolder使用ThreadLocal保证数据的隔离，并在方法调用结束后，调用remove方法移除数据源变量，保证不会发生内存泄露。  

上述的AOP逻辑本项目实现了两种方式：
- DynamicDataSourceAspect
    - 通过方法名称判断是否走从库，目前该类中定义以get、query开头的方法就会走从库。
    - 这种方法无法避免"写完读"不一致问题。  
    - 目前在项目中暂时注释掉了这个AOP逻辑。 
- ReadOnlyAspect
    - 单独定义一个注解ReadOnly，标注该方法的注解走从库，否则走主库。
    - 侵入性比较强，用户必须自己去写上注解标明是否走从库。
    - 目前在项目中的ProductService中配置了该注解。


## 参考资料  
- [Spring Boot 和 MyBatis 实现多数据源、动态数据源切换](https://github.com/helloworlde/SpringBoot-DynamicDataSource)  
    - 参考这个居多，毕竟方案都一样，主要自己写一遍，踩踩坑
- [SpringAOP获取方法参数上的注解](https://blog.csdn.net/laoxilaoxi_/article/details/97178494)
