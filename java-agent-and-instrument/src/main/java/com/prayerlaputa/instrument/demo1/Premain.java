package com.prayerlaputa.instrument.demo1;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class Premain {
    /**
     * addTransformer 方法并没有指明要转换哪个类。转换发生在 premain 函数执行之后，main 函数执行之前，这时每装载一个类，transform 方法就会执行一次，看看是否需要转换，所以，在 transform（Transformer 类中）方法中，程序用 className.equals("TransClass") 来判断当前的类是否需要转换。
     *
     * 作者：猿码架构
     * 链接：https://www.jianshu.com/p/b72f66da679f
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param agentArgs
     * @param inst
     * @throws ClassNotFoundException
     * @throws UnmodifiableClassException
     */
    public static void premain(String agentArgs, Instrumentation inst) throws ClassNotFoundException, UnmodifiableClassException {
        inst.addTransformer(new Transformer());
    }
}
