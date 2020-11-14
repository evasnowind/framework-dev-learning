package com.prayerlaputa.bytebuddyaop.agent.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class JvmStack {

    private static final long ONE_MB_SIZE = 1048576L;

    static void printMemoryInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();

        String info = String.format("\ninit: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                headMemory.getInit() / ONE_MB_SIZE + "MB",
                headMemory.getMax() / ONE_MB_SIZE + "MB",
                headMemory.getUsed() / ONE_MB_SIZE + "MB",
                headMemory.getCommitted() / ONE_MB_SIZE + "MB",
                headMemory.getUsed() * 100 / headMemory.getCommitted() + "%"
        );

        System.out.print(info);

        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();

        info = String.format("init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                nonheadMemory.getInit() / ONE_MB_SIZE + "MB",
                nonheadMemory.getMax() / ONE_MB_SIZE + "MB",
                nonheadMemory.getUsed() / ONE_MB_SIZE + "MB",
                nonheadMemory.getCommitted() / ONE_MB_SIZE + "MB",
                nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%"

        );
        System.out.println(info);

    }

    static void printGCInfo() {
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            System.out.println(info);
        }
    }
}
