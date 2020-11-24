# 自定义cache注解

实现如下功能：
- 60s超时
- 自定义缓存数量
- 默认LRU策略

## 涉及技术  
- Guava: 用作本地缓存
    - 方便复用限制使用大小等功能
- spring factories 配置文件的支持

## 附赠  

给出了几种解决被注解方法在同一个类内其他方法调用、导致AOP失效的解决方案，具体解析参见
https://blog.csdn.net/evasnowind/article/details/110136643

## TODO  
- [X] 注解默认值，@AliasFor的使用

