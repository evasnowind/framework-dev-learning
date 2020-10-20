package com.prayerlaputa.importannotation;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author chenglong.yu
 * created on 2020/10/19
 */
//@SpringBootApplication
@ComponentScan
/*导入配置类就可以了*/
@Import(MyConfig.class)
public class App2 {



    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        System.out.println(context.getBean(Dog.class));
        System.out.println(context.getBean(Cat.class));
        context.close();
    }

}
