package com.prayerlaputa.sendfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author chenglong.yu
 * created on 2020/10/29
 */
public class TransferToDemo {

    public static void main(String[] args) {
        RandomAccessFile fromFile = null;
        try {
            fromFile = new RandomAccessFile("D://fromFile.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("D://toFile.txt", "rw");
            FileChannel      toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
