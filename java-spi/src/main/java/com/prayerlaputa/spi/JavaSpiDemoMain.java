package com.prayerlaputa.spi;

import java.util.ServiceLoader;

public class JavaSpiDemoMain {
    public static void main(String[] args) {
        ServiceLoader<PrintService> serviceServiceLoader = ServiceLoader.load(PrintService.class);
        for (PrintService printService : serviceServiceLoader) {
            printService.printInfo();
        }
    }
}
