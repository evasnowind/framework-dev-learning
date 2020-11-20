# 自定义cache注解

实现如下功能：
- 60s超时
- 自定义缓存数量
- 默认LRU策略

## 涉及技术  
- Guava: 用作本地缓存
    - 方便复用限制使用大小等功能
- spring factories 配置文件的支持

## TODO  
- [ ] 注解默认值，@AliasFor的使用

