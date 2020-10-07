cglib 创建某个类A的动态代理类的模式是：

1.   查找A上的所有非final 的public类型的方法定义；

2.   将这些方法的定义转换成字节码；

3.   将组成的字节码转换成相应的代理的class对象；

4.   实现 MethodInterceptor接口，用来处理 对代理类上所有方法的请求（这个接口和JDK动态代理InvocationHandler的功能和角色是一样的）

通过继承的方式实现代理。

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Factory;

public final class ProgrammerProxy
  extends Proxy
  implements Factory
{
  private static Method m1;
  private static Method m4;
  private static Method m9;
  private static Method m6;
  private static Method m2;
  private static Method m3;
  private static Method m8;
  private static Method m7;
  private static Method m0;
  private static Method m5;
  
  public ProgrammerProxy(InvocationHandler paramInvocationHandler)
  {
    super(paramInvocationHandler);
  }
  
  public final boolean equals(Object paramObject)
  {
    try
    {
      return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Callback[] paramArrayOfCallback)
  {
    try
    {
      return (Object)this.h.invoke(this, m4, new Object[] { paramArrayOfCallback });
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final void setCallbacks(Callback[] paramArrayOfCallback)
  {
    try
    {
      this.h.invoke(this, m9, new Object[] { paramArrayOfCallback });
      return;
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final void setCallback(int paramInt, Callback paramCallback)
  {
    try
    {
      this.h.invoke(this, m6, new Object[] { Integer.valueOf(paramInt), paramCallback });
      return;
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final String toString()
  {
    try
    {
      return (String)this.h.invoke(this, m2, null);
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Class[] paramArrayOfClass, Object[] paramArrayOfObject, Callback[] paramArrayOfCallback)
  {
    try
    {
      return (Object)this.h.invoke(this, m3, new Object[] { paramArrayOfClass, paramArrayOfObject, paramArrayOfCallback });
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Callback getCallback(int paramInt)
  {
    try
    {
      return (Callback)this.h.invoke(this, m8, new Object[] { Integer.valueOf(paramInt) });
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Callback[] getCallbacks()
  {
    try
    {
      return (Callback[])this.h.invoke(this, m7, null);
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final int hashCode()
  {
    try
    {
      return ((Integer)this.h.invoke(this, m0, null)).intValue();
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Callback paramCallback)
  {
    try
    {
      return (Object)this.h.invoke(this, m5, new Object[] { paramCallback });
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  static
  {
    try
    {
      m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
      m4 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m9 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("setCallbacks", new Class[] { Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m6 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("setCallback", new Class[] { Integer.TYPE, Class.forName("net.sf.cglib.proxy.Callback") });
      m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
      m3 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("[Ljava.lang.Class;"), Class.forName("[Ljava.lang.Object;"), Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m8 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("getCallback", new Class[] { Integer.TYPE });
      m7 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("getCallbacks", new Class[0]);
      m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
      m5 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("net.sf.cglib.proxy.Callback") });
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }
}

```