参考 https://blog.csdn.net/geekjoker/article/details/80497913

目前看运行日志的话应该存在一些问题，但是基本的对象注入已ok。

test包下包含一个使用BaseMapper的示例。

模拟MyBatis：自定义一个CustomBaseMapper实现类CustomBaseMapper，用户只需要定义接口，启动时将生成该接口的代理对象，代理对象默认将调用该实现类CustomBaseMapper的方法。