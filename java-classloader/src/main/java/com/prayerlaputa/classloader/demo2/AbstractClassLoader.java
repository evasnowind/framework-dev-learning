package com.prayerlaputa.classloader.demo2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class AbstractClassLoader extends ClassLoader {

    protected String path;

    public AbstractClassLoader(String path) {
        super(null);
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            String fullPath = path + File.separator + name.replaceAll("\\.", "/") + ".class";
            File file = new File(fullPath);
            byte[] data = getClassFileBytes(file);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getClassFileBytes(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file); FileChannel fileC = fis.getChannel();
             ByteArrayOutputStream baos = new ByteArrayOutputStream(); WritableByteChannel outC = Channels.newChannel(baos);) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = fileC.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outC.write(buffer);
                buffer.clear();
            }
            return baos.toByteArray();
        }
    }

}
