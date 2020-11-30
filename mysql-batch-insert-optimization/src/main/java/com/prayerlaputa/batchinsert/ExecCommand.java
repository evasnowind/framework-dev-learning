package com.prayerlaputa.batchinsert;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;

/**
 * @author chenglong.yu
 * created on 2020/11/30
 */
public class ExecCommand implements Runnable {



    public static final int JDBC_PREPARE_STATEMENT_TYPE = 0;
    public static final int JDBC_STATEMENT_TYPE = 1;

    private int cmd;
    private int total;
    private Connection conn;
    private CountDownLatch latch;

    public ExecCommand(int cmd, int total, Connection conn, CountDownLatch latch) {
        this.cmd = cmd;
        this.total = total;
        this.conn = conn;
        this.latch = latch;
    }

    @Override
    public void run() {
        switch (cmd) {
            case JDBC_PREPARE_STATEMENT_TYPE:
                TestBatchInsert.insertBatchByPrepareStatement(conn, total, total, latch);
                break;
            case JDBC_STATEMENT_TYPE:
                TestBatchInsert.insertBatchByStatement(conn, total, total, latch);
                break;
            default:
                break;
        }
    }

}
