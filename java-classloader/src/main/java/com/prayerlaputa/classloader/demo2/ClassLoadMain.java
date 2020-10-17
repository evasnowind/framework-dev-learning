package com.prayerlaputa.classloader.demo2;

/**
 * @author limingjian
 */
public class ClassLoadMain {

    public static void main(String[] args) {
        String path = "D:/github/Java0/target/classes";
        ClassLoader1 loader1 = new ClassLoader1(path);
//        ClassLoader1 loader2 = new ClassLoader1(path);
        ClassLoader2 loader2 = new ClassLoader2(path);
        String className = "org.example.class1.Foo";
        try {
            Class foo1 = loader1.loadClass(className);
            Class foo2 = loader2.loadClass(className);
            System.out.println(foo1 == foo2);
            System.out.println(foo1.getClassLoader().getClass().getName());
            System.out.println(foo2.getClassLoader().getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
