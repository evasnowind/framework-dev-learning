package com.prayerlaputa.batchinsert;

import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenglong.yu
 * created on 2020/11/30
 */
public class TestBatchInsert {


//    private static final String LOCAL_MYSQL_URL = "jdbc:mysql://62.234.80.253:3306/mall_db_test";
//    private static final String OPTIMIZED_MYSQL_URL = "jdbc:mysql://62.234.80.253:3306/mall_db_test?useServerPrepStmts=false&rewriteBatchedStatements=true";
    private static final String LOCAL_MYSQL_URL = "jdbc:mysql://localhost:3306/mall_db_test";
    private static final String OPTIMIZED_MYSQL_URL = "jdbc:mysql://localhost:3306/mall_db_test?useServerPrepStmts=false&rewriteBatchedStatements=true";

    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PWD = "1qaz@WSX";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private static CountDownLatch latch = null;
    private static AtomicInteger atomicCounter = null;
    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 初始化测试环境
     *
     * @throws SQLException 异常时抛出
     */
    @SneakyThrows
    public static void init(String url, int total) {

        System.out.println("初始化订单表......");

        MyHikariPool.createDataSource(url, MYSQL_USER, MYSQL_PWD, 30);

        Connection conn = MyHikariPool.getConn();
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        stmt.addBatch("DROP TABLE IF EXISTS oms_order");
        stmt.addBatch("CREATE TABLE `oms_order` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',\n" +
                "  `member_id` bigint(20) NOT NULL COMMENT '用户id',\n" +
                "  `coupon_id` bigint(20) DEFAULT NULL COMMENT '优惠券id',\n" +
                "  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '提交时间',\n" +
                "  `member_username` varchar(64) DEFAULT NULL COMMENT '用户帐号',\n" +
                "  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',\n" +
                "  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '应付金额（实际支付金额）',\n" +
                "  `freight_amount` decimal(10,2) DEFAULT NULL COMMENT '运费金额',\n" +
                "  `promotion_amount` decimal(10,2) DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',\n" +
                "  `integration_amount` decimal(10,2) DEFAULT NULL COMMENT '积分抵扣金额',\n" +
                "  `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券抵扣金额',\n" +
                "  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '管理员后台调整订单使用的折扣金额',\n" +
                "  `pay_type` int(1) DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',\n" +
                "  `source_type` int(1) DEFAULT NULL COMMENT '订单来源：0->PC订单；1->app订单',\n" +
                "  `status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',\n" +
                "  `order_type` int(1) DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',\n" +
                "  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司(配送方式)',\n" +
                "  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流单号',\n" +
                "  `auto_confirm_day` int(11) DEFAULT NULL COMMENT '自动确认时间（天）',\n" +
                "  `integration` int(11) DEFAULT NULL COMMENT '可以获得的积分',\n" +
                "  `growth` int(11) DEFAULT NULL COMMENT '可以活动的成长值',\n" +
                "  `promotion_info` varchar(100) DEFAULT NULL COMMENT '活动信息',\n" +
                "  `bill_type` int(1) DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',\n" +
                "  `bill_header` varchar(200) DEFAULT NULL COMMENT '发票抬头',\n" +
                "  `bill_content` varchar(200) DEFAULT NULL COMMENT '发票内容',\n" +
                "  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '收票人电话',\n" +
                "  `bill_receiver_email` varchar(64) DEFAULT NULL COMMENT '收票人邮箱',\n" +
                "  `receiver_name` varchar(100) NOT NULL COMMENT '收货人姓名',\n" +
                "  `receiver_phone` varchar(32) NOT NULL COMMENT '收货人电话',\n" +
                "  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',\n" +
                "  `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',\n" +
                "  `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',\n" +
                "  `receiver_region` varchar(32) DEFAULT NULL COMMENT '区',\n" +
                "  `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT '详细地址',\n" +
                "  `note` varchar(500) DEFAULT NULL COMMENT '订单备注',\n" +
                "  `confirm_status` int(1) DEFAULT NULL COMMENT '确认收货状态：0->未确认；1->已确认',\n" +
                "  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态：0->未删除；1->已删除',\n" +
                "  `use_integration` int(11) DEFAULT NULL COMMENT '下单时使用的积分',\n" +
                "  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',\n" +
                "  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',\n" +
                "  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',\n" +
                "  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',\n" +
                "  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表'");
        stmt.executeBatch();
        conn.commit();
        conn.close();

        System.out.println("初始化订单表结束.");

        latch = new CountDownLatch(total);
        atomicCounter = new AtomicInteger(total);
        System.out.println("计数器latch=" + total);
    }

    public static void cleanup() {
        MyHikariPool.closeConnection();
    }


    /**
     * JDBC Statement
     * @param total
     * @param batch
     */
    @SneakyThrows
    public static void testJdbcStatementBatchInsert(int total, int batch) {

        System.out.println("--------A组测试: JDBC Statement Batch插入----------");

        init(LOCAL_MYSQL_URL, total);
        long start = System.currentTimeMillis();
        //获得数据库连接
        Class.forName(MYSQL_DRIVER);
        Connection conn = DriverManager.getConnection(LOCAL_MYSQL_URL, MYSQL_USER, MYSQL_PWD);

        insertBatchByStatement(conn, total, batch, latch);

        long costTime = System.currentTimeMillis() - start;

        System.out.println("--------A组测试: JDBC Statement Batch插入"+ total + "条(每批插入："+ batch +")，耗时：" + costTime + "ms ----------");
        System.out.println("--------A组测试: JDBC Statement Batch插入 结束！----------");
        System.out.println();

        cleanup();
    }

    @SneakyThrows
    public static void insertBatchByStatement(Connection conn, int total, int batch, CountDownLatch latch) {

        conn.setAutoCommit(false);

        int cnt = total / batch;
        int idx = 0;
        while(idx < cnt) {
//            System.out.println("idx=" + idx + " cnt=" + cnt);
            Statement stmt = conn.createStatement();
            for (int i = 0; i < batch; i++) {
                String sql = "insert oms_order(member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, receiver_name, receiver_phone, delete_status) values(1,2,\"abc\", \"2020-09-01 00:00:00\", \"aaa\", 1000, 1000, 100, 100, 10, 10, 0, \"ccc\", \"15811111111\", 0)";
                stmt.addBatch(sql);
                latch.countDown();
            }

            stmt.executeBatch();
            conn.commit();
            stmt.close();

            idx++;
        }

//        conn.close();
    }

    @SneakyThrows
    public static void testJdbcPrepareBatchInsert(int total, int batch) {
        System.out.println("--------B组测试: JDBC PrepareStatement Batch插入----------");

        init(LOCAL_MYSQL_URL, total);

        long start = System.currentTimeMillis();

        Connection conn = DriverManager.getConnection(LOCAL_MYSQL_URL, MYSQL_USER, MYSQL_PWD);
        insertBatchByPrepareStatement(conn, total, batch, latch);

        long costTime = System.currentTimeMillis() - start;

        System.out.println("--------B组测试: JDBC PrepareStatement Batch插入"+ total + "条(每批插入："+ batch +")，耗时：" + costTime + "ms ----------");
        System.out.println("--------B组测试: JDBC PrepareStatement Batch插入 结束！----------");
        System.out.println();

        cleanup();
    }

    @SneakyThrows
    public static void testJdbcPrepareBatchInsertWithRewrite(int total, int batch) {
        System.out.println("--------C组测试: JDBC PrepareStatement Batch插入(重写优化)----------");

        init(OPTIMIZED_MYSQL_URL, total);

        long start = System.currentTimeMillis();

        //！！！！！区别在这里：在mysql url后面追加?useServerPrepStmts=false&rewriteBatchedStatements=true 参数，优化批量插入
        Class.forName(MYSQL_DRIVER);
        Connection conn = DriverManager.getConnection(OPTIMIZED_MYSQL_URL, MYSQL_USER, MYSQL_PWD);
        insertBatchByPrepareStatement(conn, total, batch, latch);

        long costTime = System.currentTimeMillis() - start;

        System.out.println("--------C组测试: JDBC PrepareStatement Batch插入(重写优化)"+ total + "条(每批插入："+ batch +")，耗时：" + costTime + "ms ----------");
        System.out.println("--------C组测试: JDBC PrepareStatement Batch插入(重写优化) 结束！----------");
        System.out.println();

        cleanup();
    }

    @SneakyThrows
    public static void insertBatchByPrepareStatement(Connection conn, int total, int batch, CountDownLatch latch) {

        conn.setAutoCommit(false);

        final String sql = "insert oms_order(member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, receiver_name, receiver_phone, delete_status) values(?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?)";

        long memberId = 1;
        long couponId = 1;
        long orderId = 1;
        long userNameId = 1;

        java.sql.Date date = new java.sql.Date(2020, 11, 1);
        int idx = 0;
        int cnt = total / batch;
        while(idx < cnt) {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (int i = 0; i < batch; i++) {
                statement.setLong(1, memberId);
                statement.setLong(2, couponId);
                statement.setString(3, String.valueOf(orderId));
                orderId++;

                statement.setDate(4, date);
                statement.setString(5, "user" + (userNameId++));
                statement.setBigDecimal(6, new BigDecimal("100000"));
                statement.setBigDecimal(7, BigDecimal.ZERO);
                statement.setBigDecimal(8, BigDecimal.ZERO);
                statement.setBigDecimal(9, BigDecimal.ZERO);
                statement.setBigDecimal(10, BigDecimal.ZERO);
                statement.setBigDecimal(11, BigDecimal.ZERO);
                statement.setBigDecimal(12, BigDecimal.ZERO);
                statement.setString(13, "Prayer");
                statement.setString(14, "China");
                statement.setInt(15, 0);

                statement.addBatch();
                latch.countDown();
                counter.incrementAndGet();
            }

            statement.executeBatch();
            conn.commit();
            statement.closeOnCompletion();
            idx++;
        }

//        conn.close();
    }


    @SneakyThrows
    public static void testHikariStatementBatchInsert(int total, int batch) {
        System.out.println("--------D组测试: Hikari Batch插入----------");

        init(OPTIMIZED_MYSQL_URL, total);

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(30);

        int idx = 0;
        int cnt = total / batch;
        while(idx < cnt) {
            System.out.println("idx=" + idx);
            Connection conn = MyHikariPool.getConn();
            executorService.execute(new ExecCommand(ExecCommand.JDBC_STATEMENT_TYPE, batch, conn, latch));
            idx++;
        }

        while(counter.get() <= total) {
            TimeUnit.MILLISECONDS.sleep(30);
        }

//        latch.await();

        long costTime = System.currentTimeMillis() - start;
        System.out.println("--------D组测试: Hikari Batch插入"+ total + "条(每批插入："+ batch +")，耗时：" + costTime + "ms ----------");
        System.out.println("--------D组测试: Hikari Batch插入结束！----------");

        cleanup();
    }

    @SneakyThrows
    public static void testHikariPrepareStatementBatchInsert(int total, int batch) {
        System.out.println("--------E组测试: Hikari PrepareStatement Batch插入----------");

        init(OPTIMIZED_MYSQL_URL, total);

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(30);

        int idx = 0;
        int cnt = total / batch;
        while(idx < cnt) {
            Connection conn = MyHikariPool.getConn();
            executorService.execute(new ExecCommand(ExecCommand.JDBC_PREPARE_STATEMENT_TYPE, batch, conn, latch));
            System.out.println("counter="+counter);
        }


        while(counter.get() <= total) {
            TimeUnit.MILLISECONDS.sleep(30);
        }


//        latch.await();

        long costTime = System.currentTimeMillis() - start;
        System.out.println("--------E组测试: Hikari PrepareStatement Batch插入"+ total + "条(每批插入："+ batch +")，耗时：" + costTime + "ms ----------");
        System.out.println("--------E组测试: Hikari PrepareStatement Batch插入结束！----------");

        cleanup();
    }



    public static void main(String[] args) throws Exception {

        int total = 1000000, batch = 100000;

        testJdbcStatementBatchInsert(total, batch);

        testJdbcPrepareBatchInsert(total, batch);

        testJdbcPrepareBatchInsertWithRewrite(total, batch);

        //使用hikari 连接池，复用连接。但目前有点问题，有时间了再调。
//        testHikariStatementBatchInsert(total, batch);

//        testHikariPrepareStatementBatchInsert(total, batch);
    }

}
