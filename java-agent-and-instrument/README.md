# 说明

## Java Agent简介
参考这篇文章： https://www.jianshu.com/p/63c328ca208d

### 主程序运行前的agent
参见TestAgent

在主程序运行之前的agent模式有一些缺陷，例如需要在主程序运行前就指定javaagent参数，premain方法中代码出现异常会导致主程序启动失败等，为了解决这些问题，JDK1.6以后提供了在程序运行之后改变程序的能力。它的实现步骤和之前的模式类似。

### 程序运行时attach到主程序，动态改变程序

参见TestAgent2

### Instrument  
内容略多，demo没写完，可以参考这个帖子：https://www.jianshu.com/p/b72f66da679f


## 注意

编译打包时可能会遇到maven报错
```text
The packaging for this project did not assign a file to the build artifact
```

参考这篇文章即可：[Maven报错：The packaging for this project did not assign a file to the build artifact](https://blog.csdn.net/gao_zhennan/article/details/89713407)