package assemblebean.xmlconfig;

import com.prayerlaputa.assemblebean.xmlconfig.CollectionBean;
import com.prayerlaputa.assemblebean.xmlconfig.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
public class TestXmlDependencyInject {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlbeans.xml");

        CollectionBean collectionBean = (CollectionBean) context.getBean("collectionBean");
        collectionBean.printAllProperties();

        UserService userService = (UserService) context.getBean("userService");
        userService.printService();

    }
}
