# 说明

本意是想写一个自定义starter的demo，但是由于项目组织关系子module不是继承自spring-boot-starter，导致打包时有问题，暂时先这样，大致解决方案可以参考：  
- https://docs.spring.io/spring-boot/docs/2.0.0.M5/reference/htmlsingle/#boot-features-custom-starter-naming
- https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-build-systems
- https://stackoverflow.com/questions/21317006/spring-boot-parent-pom-when-you-already-have-a-parent-pom/21318359#21318359

想了解如何自定义starter的，可以参考 
- https://www.cnblogs.com/tjudzj/p/8758391.html
    - 对应代码为：https://github.com/RitterHou/learn-spring-boot-starter

一个完整的spring boot starter的实例可以参考这个repo: https://github.com/evasnowind/tiny-school-spring-boot-starter