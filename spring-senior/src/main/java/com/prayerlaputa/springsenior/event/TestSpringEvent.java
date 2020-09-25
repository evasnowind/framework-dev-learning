package com.prayerlaputa.springsenior.event;

import com.prayerlaputa.springsenior.bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenglong.yu
 * created on 2020/9/25
 */
public class TestSpringEvent {

//    private static ClassPathXmlApplicationContext appContext;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                new String[]{"appContext.xml"}
        );

        Student student = appContext.getBean(Student.class);
        System.out.println("student=" + student);


        StudentAddBean addBean = new StudentAddBean();
        addBean.setApplicationContext(appContext);
        addBean.addStudent("prayer");
        System.out.println("发布添加学生消息...");

        // 每次new ClassPathXmlApplicationContext,会创建一个新的ApplicationContext实例.
        ClassPathXmlApplicationContext appContext1 = new ClassPathXmlApplicationContext(
                new String[]{"appContext.xml"}
        );

        System.out.println("appContext=" + appContext.toString());
        System.out.println("appContext1=" + appContext1.toString());
        String text = "appContext==appContext1? " + (appContext == appContext1);
        System.out.println(text);
        System.out.println("appContext.equals(appContext1)? " + (appContext.equals(appContext1)));

        System.out.println("appContext_identityHashCode=" + System.identityHashCode(appContext));
        System.out.println("appContext1_identityHashCode=" + System.identityHashCode(appContext1));

        Student student1 = appContext.getBean(Student.class);
        // student和student1都是appContext.getBean(Student.class),得到的是同一个Object， 单例
        System.out.println("student==student1? " + (student == student1));
        Student student2 = appContext1.getBean(Student.class);
        System.out.println("student==student2? " + (student == student2));

    }
}
