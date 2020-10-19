package com.prayerlaputa.spi;

public class PrintServiceImplExt implements PrintService {
    @Override
    public void printInfo() {
        System.out.println("java spi demo: PrintServiceImpl(ext)");
    }
}
