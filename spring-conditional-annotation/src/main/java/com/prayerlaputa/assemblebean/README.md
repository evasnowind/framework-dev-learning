# spring bean注入

## XML配置注入  
- 代码：`com.prayerlaputa.assemblebean.xmlconfig`
- 配置文件：`resources/xmlbeans.xml`
- 测试：`test.com.prayerlaputa.assemblebean.xmlconfig.TestXmlDependencyInject` 
- 程序说明：创建了两个bean，CollectionBean用于演示使用XML注入数组、List、Map、Properties类型的成员变量；UserService对象则演示bean之间的注入, UserDAO对象注入到UserService中。

## 半自动注解配置  
- 代码：`com.prayerlaputa.assemblebean.halfauto`
- 配置文件：`resources/halfautoannotation.xml`
- 测试：`test.com.prayerlaputa.assemblebean.halfauto.TestHalfAutoAnnotationInjection` 
- 程序说明：分别创建HalfAutoUserDao、HalfAutoUserService两个bean，然后将HalfAutoUserDao自动注入到HalfAutoUserService。使用@Service @Repository等注解 + xml配置中指定bean目录。

## java config配置
- 代码：`com.prayerlaputa.assemblebean.javaconfig`
- 配置文件：`resources/javaconfig.xml`
- 测试：`test.com.prayerlaputa.assemblebean.javaconfig.TestJavaConfig` 
- 程序说明：通过@Configuration，@Bean创建了两个bean，Car、Person，将Car注入到Person的bean中。


## autoconfig配置  
- 代码：`com.prayerlaputa.assemblebean.autoinjection`
- 配置文件：无
- 测试：`test.com.prayerlaputa.assemblebean.autoinjection.TestAutoInjection` 
- 程序说明：通过@Configuration，@Bean 将创建2个bean，Person必然创建，而Cat、Dog只会创建其中之一、并注入到Person的animal成员变量中。Cat、Dog都是Animal子类，给Dog的创建方法上加@Conditional注解，具体判断条件由PetCondition类中定义，目前的实现逻辑是：如果已经有了person对象、且容器中没有cat对象，才会创建dog对象。
    - 此处只是一个示例，PetCondition可以进一步修改，比如通过配置文件，或是通过输入参数指定创建Cat还是Dog。这个就不继续写了。
    - 其他类似的注解参见下面。

```text
@ConditionalOnSingleCandidate	当给定类型的bean存在并且指定为Primary的给定类型存在时,返回true
@ConditionalOnMissingBean	当给定的类型、类名、注解、昵称在beanFactory中不存在时返回true.各类型间是or的关系
@ConditionalOnBean	与上面相反，要求bean存在
@ConditionalOnMissingClass	当给定的类名在类路径上不存在时返回true,各类型间是and的关系
@ConditionalOnClass	与上面相反，要求类存在
@ConditionalOnCloudPlatform	当所配置的CloudPlatform为激活时返回true
@ConditionalOnExpression	spel表达式执行为true
@ConditionalOnJava	运行时的java版本号是否包含给定的版本号.如果包含,返回匹配,否则,返回不匹配
@ConditionalOnProperty	要求配置属性匹配条件
@ConditionalOnJndi	给定的jndi的Location 必须存在一个.否则,返回不匹配
@ConditionalOnNotWebApplication	web环境不存在时
@ConditionalOnWebApplication	web环境存在时
@ConditionalOnResource	要求制定的资源存在
```


## 参考资料  
- [spring依赖注入与注解实例](https://blog.csdn.net/feinifi/article/details/88839443)
- [spring使用JavaConfig进行配置](https://blog.csdn.net/peng86788/article/details/81188049)
- [SpringBoot基础篇Bean之条件注入@Condition使用姿势](https://blog.csdn.net/liuyueyi25/article/details/83244263)