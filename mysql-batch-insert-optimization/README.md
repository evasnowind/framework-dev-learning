## mysql批量插入100w条数据  

按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。  

参见`com.prayerlaputa.batchinsert.TestBatchInsert`。目前已实现：
- 使用JDBC Statement(Batch方式)分批写入数据库
    - 参见TestBatchInsert#testJdbcStatementBatchInsert()
- 使用JDBC PrepareStatement(Batch方式，但没有打开mysql批量功能)
    - 参见TestBatchInsert#testJdbcPrepareBatchInsert()
- 使用JDBC PrepareStatement(Batch方式，并打开mysql批量功能)
    - 参见TestBatchInsert#testJdbcPrepareBatchInsertWithRewrite()
- mysql存储过程
    - 参见resources/

目前还有问题、没来得及完善的代码：
- [ ] 使用Hikari线程池+JDBC Statement(Batch方式)  
- [ ] 使用Hikari线程池+JDBC PrepareStatement(Batch方式)  


## 测试  

需要先在本地创建一个mysql数据库mall_db_test。

### Java程序测试

运行TestBatchInsert main方法即可。  

在我本机环境(win 10 + Intel i7 + 16G内存)下运行结果如下：
```text
--------A组测试: JDBC Statement Batch插入----------
初始化订单表......
初始化订单表结束.
计数器latch=1000000
--------A组测试: JDBC Statement Batch插入1000000条(每批插入：100000)，耗时：226988ms ----------
--------A组测试: JDBC Statement Batch插入 结束！----------

--------B组测试: JDBC PrepareStatement Batch插入----------
初始化订单表......
初始化订单表结束.
计数器latch=1000000
--------B组测试: JDBC PrepareStatement Batch插入1000000条(每批插入：100000)，耗时：373306ms ----------
--------B组测试: JDBC PrepareStatement Batch插入 结束！----------

--------C组测试: JDBC PrepareStatement Batch插入(重写优化)----------
初始化订单表......
初始化订单表结束.
计数器latch=1000000
--------C组测试: JDBC PrepareStatement Batch插入(重写优化)1000000条(每批插入：100000)，耗时：23201ms ----------
--------C组测试: JDBC PrepareStatement Batch插入(重写优化) 结束！----------
```

可以看到 JDBC PrepareStatement Batch插入(重写优化) 方式最快，当然，如果加上多线程运行、从线程池拿多个连接并行执行，速度会更快，这块试验没顾上。

### 存储过程测试  

存储过程参见`resources/batchinsert_procedure.sql`，执行成功后，调用存储过程即可`call mall_db_test.batchinsert(1000000) `

在本机执行结果如下：
```text
mysql> DELIMITER $$
mysql> create procedure mall_db_test.batchinsert(cnt int)
    -> begin
    -> declare i int default 0;
    -> start transaction;
    -> while i<cnt
    -> do
    ->    insert oms_order(member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, receiver_name, receiver_phone, delete_status) values(1,2,"abc", "2020-09-01 00:00:00", "aaa", 1000, 1000, 100, 100, 10, 10, 0, "ccc", "15811111111", 0);
    -> set i=i+1;
    -> end while;
    -> commit;
    -> end  $$
Query OK, 0 rows affected (0.00 sec)

mysql> call mall_db_test.batchinsert(1000000)$$
Query OK, 0 rows affected (22.27 sec)
```

可以看到存储过程只用了22s。

## 程序说明

为避免相互干扰，TestBatchInsert在每次运行单个测试时，会运行`init()`方法将删掉已有的`mall_db_test.oms_order`、创建一个空表。


