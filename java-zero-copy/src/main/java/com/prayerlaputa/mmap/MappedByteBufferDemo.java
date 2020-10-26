package com.prayerlaputa.mmap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * @author chenglong.yu
 * created on 2020/10/29
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) {
        File file = new File("D://data.txt");
        long len = file.length();
        byte[] ds = new byte[(int) len];

        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "r")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int offset = 0; offset < len; offset++) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }

            Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");
            while (scan.hasNext()) {
                System.out.print(scan.next() + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
