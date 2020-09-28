package com.prayerlaputa.connectionpool.test.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author chenglong.yu
 * created on 2020/9/1
 */
public class PropertiesHolder extends Properties {

    private static final String PROPERTY_FILE = "datasource.properties";
    private static PropertiesHolder propertiesHolder = new PropertiesHolder();

    private PropertiesHolder() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)){
            this.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PropertiesHolder getInstance() {
        return propertiesHolder;
    }
}
