package com.prayerlaputa.spi;

public class PrintServiceImpl implements PrintService {
    @Override
    public void printInfo() {
        System.out.println("java spi demo: PrintServiceImpl(default)");
    }
}
