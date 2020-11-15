package assemblebean.javaconfig;

import com.prayerlaputa.assemblebean.javaconfig.Car;
import com.prayerlaputa.assemblebean.javaconfig.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class TestJavaConfig {

    @Test
    public void testJavaConfig() {
        //读取配置文件
        ApplicationContext ctx=new ClassPathXmlApplicationContext("javaconfig.xml");
        Car car = ctx.getBean("myCar", Car.class);
        System.out.println(car);
        Person person = ctx.getBean("person", Person.class);
        System.out.println(person);
    }
}
