package com.prayerlaputa.cas;

public class CasDemo {

    public static void main(String[] args) {
        MyCacheLong myCacheLong = new MyCacheLong(101);
        myCacheLong.getAndUpdate(199);
        long before = myCacheLong.getAndUpdate(341);

        System.out.println("---myCacheLong before="+before +" get()=" + myCacheLong.get());
    }
}
