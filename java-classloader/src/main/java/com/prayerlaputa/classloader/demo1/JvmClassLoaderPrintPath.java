package com.prayerlaputa.classloader.demo1;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * @author chenglong.yu
 * created on 2020/10/17
 */
public class JvmClassLoaderPrintPath {
    public static void main(String[] args) {
        //启动类加载器
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urls) {
            System.out.println("==>" + url.toExternalForm());
        }
        //扩展类加载器
        printClassLoader("扩展类加载器", JvmClassLoaderPrintPath.class.getClassLoader().getParent());

        //应用类加载器
        printClassLoader("应用类加载器", JvmClassLoaderPrintPath.class.getClassLoader());
    }

    public static void printClassLoader(String name, ClassLoader classLoader) {
        if (null != classLoader) {
            System.out.println(name + " classloader -> " + classLoader.toString());
            printURLForClassLoader(classLoader);
        } else {
            System.out.println(name + " classloader -> null");
        }
    }

    public static void printURLForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> " + p.toString());
        }

    }

    private static Object insightField(Object obj, String fName) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fName);
            } else {
                f = obj.getClass().getDeclaredField(fName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
