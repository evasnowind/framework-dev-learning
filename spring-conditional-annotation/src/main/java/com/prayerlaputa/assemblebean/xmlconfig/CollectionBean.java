package com.prayerlaputa.assemblebean.xmlconfig;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author chenglong.yu
 * created on 2020/11/13
 */
@Service
public class CollectionBean {

    private String[] arrs;
    private List<String> list;
    private Map<String, String> map;
    private Properties properties;

    public void setArrs(String[] arrs) {
        this.arrs = arrs;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void printAllProperties() {
        System.out.print("[");
        for (int i = 0; i < arrs.length; i++) {
            System.out.print(arrs[i] + ",");
        }
        System.out.println("]");
        System.out.println(list);
        System.out.println(map);
        System.out.println(properties);
    }
}
