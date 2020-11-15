package assemblebean.autoinjection;

import com.prayerlaputa.assemblebean.autoinjection.BeanConfig;
import com.prayerlaputa.assemblebean.autoinjection.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class TestAutoInjection {

    @Test
    public void testConditionAnnotation() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        Person person = (Person)context.getBean("person");
        System.out.println(person.getAnimal().getName());
    }
}
