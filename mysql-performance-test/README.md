## mysql基准测试

[TOC]


### 测试环境说明 

#### 硬件

- 腾讯云标准型SA2
    - 1核 2GB 1Mbps
    - 系统盘：高性能云硬盘(50G)


#### 软件

- centos 7.5 64位
- mysql 5.7.31
- sysbench 1.0.20


### 测试过程  

使用sysbench。

#### 1. 测试innodb 100w条记录的读写性能

##### 1.1 生成100w数据

此处偷懒了，直接使用了sysbench默认的脚本创建数据库表，如果需要测试特定的表，需要修改`/usr/share/sysbench`下的脚本，基本只需要修改`oltp_common.lua`脚本即可，这个需要花时间细致的修改，这次就不在这方面多花时间了。本次主要目的是做基准测试、看看Mysql性能。

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20  oltp_read_write prepare
```


##### 1.2 进行读写测试

###### 1.2.1 第一次直接执行100w次读写

执行命令：  
```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
```

参数说明：
- 指定测试mysql，默认存储引擎是innodb，指定mysql地址、端口、用户等信息
- 选取测试的数据库为sbtest，每个表的大小为100w条数据，创建10张表
- 测试时间300s, 20个线程同时测试
- 输出测试结果95分位
- 每隔10s输出一次当前测试情况
- 选取sysbench中的oltp_read_write.lua，测试读写情况


注意此处没有预热，先观察下没有预热情况下的读写性能，得到如下执行结果：

```
[root@VM_0_5_centos ~]# sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
sysbench 1.0.20 (using bundled LuaJIT 2.1.0-beta2)

Running the test with following options:
Number of threads: 20
Report intermediate results every 10 second(s)
Initializing random number generator from current time


Initializing worker threads...

Threads started!

[ 10s ] thds: 20 tps: 115.28 qps: 2327.70 (r/w/o: 1633.92/461.22/232.56) lat (ms,95%): 337.94 err/s: 0.00 reconn/s: 0.00
[ 20s ] thds: 20 tps: 107.65 qps: 2154.96 (r/w/o: 1508.64/431.01/215.31) lat (ms,95%): 350.33 err/s: 0.00 reconn/s: 0.00
[ 30s ] thds: 20 tps: 107.57 qps: 2155.23 (r/w/o: 1509.13/430.97/215.13) lat (ms,95%): 337.94 err/s: 0.00 reconn/s: 0.00
[ 40s ] thds: 20 tps: 102.08 qps: 2027.14 (r/w/o: 1414.88/408.11/204.16) lat (ms,95%): 369.77 err/s: 0.00 reconn/s: 0.00
[ 50s ] thds: 20 tps: 111.17 qps: 2239.64 (r/w/o: 1571.11/446.19/222.34) lat (ms,95%): 337.94 err/s: 0.00 reconn/s: 0.00
[ 60s ] thds: 20 tps: 118.82 qps: 2376.16 (r/w/o: 1663.62/474.89/237.65) lat (ms,95%): 297.92 err/s: 0.00 reconn/s: 0.00
[ 70s ] thds: 20 tps: 118.57 qps: 2369.40 (r/w/o: 1657.88/474.48/237.04) lat (ms,95%): 325.98 err/s: 0.00 reconn/s: 0.00
[ 80s ] thds: 20 tps: 125.53 qps: 2512.64 (r/w/o: 1758.58/502.91/251.15) lat (ms,95%): 287.38 err/s: 0.00 reconn/s: 0.00
[ 90s ] thds: 20 tps: 115.44 qps: 2298.71 (r/w/o: 1609.27/458.76/230.68) lat (ms,95%): 314.45 err/s: 0.00 reconn/s: 0.00
[ 100s ] thds: 20 tps: 120.28 qps: 2412.81 (r/w/o: 1689.53/482.52/240.76) lat (ms,95%): 314.45 err/s: 0.00 reconn/s: 0.00
[ 110s ] thds: 20 tps: 123.70 qps: 2460.59 (r/w/o: 1719.30/494.00/247.30) lat (ms,95%): 287.38 err/s: 0.00 reconn/s: 0.00
[ 120s ] thds: 20 tps: 121.74 qps: 2455.40 (r/w/o: 1720.96/490.86/243.58) lat (ms,95%): 287.38 err/s: 0.00 reconn/s: 0.00
[ 130s ] thds: 20 tps: 129.32 qps: 2580.95 (r/w/o: 1808.11/514.19/258.64) lat (ms,95%): 292.60 err/s: 0.00 reconn/s: 0.00
[ 140s ] thds: 20 tps: 125.38 qps: 2508.77 (r/w/o: 1756.50/501.51/250.76) lat (ms,95%): 282.25 err/s: 0.00 reconn/s: 0.00
[ 150s ] thds: 20 tps: 128.26 qps: 2564.90 (r/w/o: 1794.64/513.74/256.52) lat (ms,95%): 287.38 err/s: 0.00 reconn/s: 0.00
[ 160s ] thds: 20 tps: 129.90 qps: 2596.47 (r/w/o: 1817.98/518.79/259.70) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 170s ] thds: 20 tps: 130.30 qps: 2597.18 (r/w/o: 1816.16/520.32/260.71) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 180s ] thds: 20 tps: 134.10 qps: 2685.40 (r/w/o: 1881.30/535.90/268.20) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 190s ] thds: 20 tps: 131.38 qps: 2636.25 (r/w/o: 1842.79/530.91/262.56) lat (ms,95%): 282.25 err/s: 0.00 reconn/s: 0.00
[ 200s ] thds: 20 tps: 134.40 qps: 2687.60 (r/w/o: 1883.30/535.30/269.00) lat (ms,95%): 282.25 err/s: 0.00 reconn/s: 0.00
[ 210s ] thds: 20 tps: 130.51 qps: 2610.40 (r/w/o: 1827.61/521.76/261.03) lat (ms,95%): 292.60 err/s: 0.00 reconn/s: 0.00
[ 220s ] thds: 20 tps: 134.91 qps: 2687.87 (r/w/o: 1880.32/537.73/269.82) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 230s ] thds: 20 tps: 131.30 qps: 2633.77 (r/w/o: 1845.48/525.69/262.60) lat (ms,95%): 277.21 err/s: 0.00 reconn/s: 0.00
[ 240s ] thds: 20 tps: 133.78 qps: 2676.67 (r/w/o: 1872.87/536.23/267.57) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 250s ] thds: 20 tps: 134.10 qps: 2681.82 (r/w/o: 1876.34/537.28/268.19) lat (ms,95%): 292.60 err/s: 0.00 reconn/s: 0.00
[ 260s ] thds: 20 tps: 136.32 qps: 2711.48 (r/w/o: 1896.94/541.90/272.65) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 270s ] thds: 20 tps: 134.20 qps: 2692.50 (r/w/o: 1884.80/539.30/268.40) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 280s ] thds: 20 tps: 133.03 qps: 2664.24 (r/w/o: 1866.85/531.33/266.06) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 290s ] thds: 20 tps: 134.52 qps: 2687.68 (r/w/o: 1880.73/537.90/269.05) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 300s ] thds: 20 tps: 130.74 qps: 2615.97 (r/w/o: 1830.71/523.77/261.49) lat (ms,95%): 277.21 err/s: 0.00 reconn/s: 0.00
SQL statistics:
    queries performed:
        read:                            527282
        write:                           150652
        other:                           75326
        total:                           753260
    transactions:                        37663  (125.47 per sec.)
    queries:                             753260 (2509.43 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.1706s
    total number of events:              37663

Latency (ms):
         min:                                    7.13
         avg:                                  159.35
         max:                                  873.88
         95th percentile:                      292.60
         sum:                              6001658.82

Threads fairness:
    events (avg/stddev):           1883.1500/14.40
    execution time (avg/stddev):   300.0829/0.05
```

###### 1.2.2 第二次执行100w次读写  

在第一次执行完成后，再一次执行相同的压测命令：

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
```

也就是说，将第一次执行当做预热，然后得到了如下结果：

```
[root@VM_0_5_centos ~]# sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
sysbench 1.0.20 (using bundled LuaJIT 2.1.0-beta2)

Running the test with following options:
Number of threads: 20
Report intermediate results every 10 second(s)
Initializing random number generator from current time


Initializing worker threads...

Threads started!

[ 10s ] thds: 20 tps: 134.18 qps: 2711.59 (r/w/o: 1901.91/539.32/270.36) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 20s ] thds: 20 tps: 137.73 qps: 2743.17 (r/w/o: 1918.90/549.01/275.26) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 30s ] thds: 20 tps: 135.00 qps: 2700.12 (r/w/o: 1889.21/540.70/270.20) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 40s ] thds: 20 tps: 133.61 qps: 2680.90 (r/w/o: 1880.57/533.12/267.21) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 50s ] thds: 20 tps: 133.36 qps: 2673.78 (r/w/o: 1869.10/537.96/266.73) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 60s ] thds: 20 tps: 139.24 qps: 2776.62 (r/w/o: 1945.18/553.07/278.38) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 70s ] thds: 20 tps: 132.31 qps: 2646.75 (r/w/o: 1852.27/529.75/264.72) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 80s ] thds: 20 tps: 138.52 qps: 2764.91 (r/w/o: 1934.31/553.56/277.03) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 90s ] thds: 20 tps: 133.20 qps: 2673.72 (r/w/o: 1874.85/532.48/266.39) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 100s ] thds: 20 tps: 139.13 qps: 2783.76 (r/w/o: 1946.06/559.43/278.27) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 110s ] thds: 20 tps: 133.60 qps: 2673.61 (r/w/o: 1872.01/534.40/267.20) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 120s ] thds: 20 tps: 137.21 qps: 2739.02 (r/w/o: 1916.55/548.04/274.42) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 130s ] thds: 20 tps: 136.59 qps: 2736.29 (r/w/o: 1916.22/546.88/273.19) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 140s ] thds: 20 tps: 133.31 qps: 2667.76 (r/w/o: 1865.01/536.33/266.42) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 150s ] thds: 20 tps: 136.29 qps: 2728.42 (r/w/o: 1910.27/545.36/272.78) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 160s ] thds: 20 tps: 137.75 qps: 2747.96 (r/w/o: 1925.34/547.21/275.41) lat (ms,95%): 248.83 err/s: 0.00 reconn/s: 0.00
[ 170s ] thds: 20 tps: 136.25 qps: 2727.97 (r/w/o: 1910.08/545.29/272.60) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 180s ] thds: 20 tps: 139.96 qps: 2799.77 (r/w/o: 1958.39/561.45/279.93) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 190s ] thds: 20 tps: 136.37 qps: 2724.15 (r/w/o: 1907.31/544.09/272.74) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 200s ] thds: 20 tps: 137.35 qps: 2748.81 (r/w/o: 1925.74/548.38/274.69) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 210s ] thds: 20 tps: 138.32 qps: 2760.41 (r/w/o: 1930.89/552.88/276.64) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 220s ] thds: 20 tps: 136.57 qps: 2730.27 (r/w/o: 1912.63/544.89/272.75) lat (ms,95%): 272.27 err/s: 0.00 reconn/s: 0.00
[ 230s ] thds: 20 tps: 135.90 qps: 2725.03 (r/w/o: 1905.42/547.41/272.20) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 240s ] thds: 20 tps: 139.06 qps: 2780.47 (r/w/o: 1948.78/553.56/278.13) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 250s ] thds: 20 tps: 136.23 qps: 2719.64 (r/w/o: 1902.58/544.61/272.45) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 260s ] thds: 20 tps: 136.39 qps: 2736.95 (r/w/o: 1913.82/550.35/272.77) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 270s ] thds: 20 tps: 141.91 qps: 2829.29 (r/w/o: 1980.91/564.56/283.83) lat (ms,95%): 248.83 err/s: 0.00 reconn/s: 0.00
[ 280s ] thds: 20 tps: 138.60 qps: 2776.59 (r/w/o: 1944.46/554.92/277.21) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 290s ] thds: 20 tps: 137.72 qps: 2747.78 (r/w/o: 1923.26/549.08/275.44) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 300s ] thds: 20 tps: 139.27 qps: 2798.45 (r/w/o: 1958.44/561.47/278.54) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
SQL statistics:
    queries performed:
        read:                            574434
        write:                           164124
        other:                           82062
        total:                           820620
    transactions:                        41031  (136.74 per sec.)
    queries:                             820620 (2734.82 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.0629s
    total number of events:              41031

Latency (ms):
         min:                                    6.62
         avg:                                  146.24
         max:                                  709.59
         95th percentile:                      262.64
         sum:                              6000331.95

Threads fairness:
    events (avg/stddev):           2051.5500/16.35
    execution time (avg/stddev):   300.0166/0.02
```



###### 1.2.3 第三次执行100w次读写 

还是这条命令，再次执行看下：

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
```

最后执行结果如下：

```
[root@VM_0_5_centos ~]# sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write run
sysbench 1.0.20 (using bundled LuaJIT 2.1.0-beta2)

Running the test with following options:
Number of threads: 20
Report intermediate results every 10 second(s)
Initializing random number generator from current time


Initializing worker threads...

Threads started!

[ 10s ] thds: 20 tps: 137.99 qps: 2785.30 (r/w/o: 1953.79/553.54/277.97) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 20s ] thds: 20 tps: 139.98 qps: 2796.53 (r/w/o: 1955.64/561.23/279.66) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 30s ] thds: 20 tps: 135.89 qps: 2721.84 (r/w/o: 1905.59/544.37/271.88) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 40s ] thds: 20 tps: 143.80 qps: 2874.52 (r/w/o: 2013.41/573.40/287.70) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 50s ] thds: 20 tps: 141.73 qps: 2838.10 (r/w/o: 1986.65/567.90/283.55) lat (ms,95%): 277.21 err/s: 0.00 reconn/s: 0.00
[ 60s ] thds: 20 tps: 142.10 qps: 2835.57 (r/w/o: 1984.28/567.09/284.20) lat (ms,95%): 248.83 err/s: 0.00 reconn/s: 0.00
[ 70s ] thds: 20 tps: 141.93 qps: 2838.68 (r/w/o: 1987.87/566.94/283.87) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 80s ] thds: 20 tps: 142.27 qps: 2841.13 (r/w/o: 1987.20/569.39/284.54) lat (ms,95%): 244.38 err/s: 0.00 reconn/s: 0.00
[ 90s ] thds: 20 tps: 144.78 qps: 2904.14 (r/w/o: 2034.24/580.63/289.27) lat (ms,95%): 231.53 err/s: 0.00 reconn/s: 0.00
[ 100s ] thds: 20 tps: 141.03 qps: 2822.14 (r/w/o: 1977.08/562.71/282.35) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 110s ] thds: 20 tps: 138.68 qps: 2775.16 (r/w/o: 1941.56/556.23/277.37) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 120s ] thds: 20 tps: 140.70 qps: 2799.50 (r/w/o: 1957.90/560.50/281.10) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 130s ] thds: 20 tps: 142.10 qps: 2847.50 (r/w/o: 1994.40/569.00/284.10) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 140s ] thds: 20 tps: 142.10 qps: 2847.17 (r/w/o: 1994.18/568.39/284.60) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 150s ] thds: 20 tps: 141.69 qps: 2834.74 (r/w/o: 1983.99/567.37/283.38) lat (ms,95%): 240.02 err/s: 0.00 reconn/s: 0.00
[ 160s ] thds: 20 tps: 142.69 qps: 2857.88 (r/w/o: 1999.55/572.96/285.38) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 170s ] thds: 20 tps: 148.01 qps: 2953.24 (r/w/o: 2067.20/590.03/296.01) lat (ms,95%): 244.38 err/s: 0.00 reconn/s: 0.00
[ 180s ] thds: 20 tps: 142.16 qps: 2845.84 (r/w/o: 1993.50/568.03/284.31) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 190s ] thds: 20 tps: 141.93 qps: 2838.87 (r/w/o: 1987.80/567.21/283.86) lat (ms,95%): 262.64 err/s: 0.00 reconn/s: 0.00
[ 200s ] thds: 20 tps: 137.03 qps: 2742.24 (r/w/o: 1919.74/548.43/274.06) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 210s ] thds: 20 tps: 141.14 qps: 2822.53 (r/w/o: 1975.28/564.97/282.28) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 220s ] thds: 20 tps: 143.06 qps: 2859.26 (r/w/o: 2001.71/571.43/286.12) lat (ms,95%): 257.95 err/s: 0.00 reconn/s: 0.00
[ 230s ] thds: 20 tps: 145.89 qps: 2911.60 (r/w/o: 2036.39/583.54/291.67) lat (ms,95%): 248.83 err/s: 0.00 reconn/s: 0.00
[ 240s ] thds: 20 tps: 142.86 qps: 2862.32 (r/w/o: 2003.09/573.42/285.81) lat (ms,95%): 244.38 err/s: 0.00 reconn/s: 0.00
[ 250s ] thds: 20 tps: 142.36 qps: 2845.35 (r/w/o: 1992.70/567.93/284.71) lat (ms,95%): 248.83 err/s: 0.00 reconn/s: 0.00
[ 260s ] thds: 20 tps: 140.64 qps: 2816.09 (r/w/o: 1971.96/562.86/281.28) lat (ms,95%): 267.41 err/s: 0.00 reconn/s: 0.00
[ 270s ] thds: 20 tps: 143.49 qps: 2865.80 (r/w/o: 2004.86/573.96/286.98) lat (ms,95%): 282.25 err/s: 0.00 reconn/s: 0.00
[ 280s ] thds: 20 tps: 145.81 qps: 2922.99 (r/w/o: 2047.60/583.76/291.63) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 290s ] thds: 20 tps: 142.26 qps: 2838.44 (r/w/o: 1985.40/568.53/284.51) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
[ 300s ] thds: 20 tps: 137.89 qps: 2755.07 (r/w/o: 1928.14/551.15/275.78) lat (ms,95%): 253.35 err/s: 0.00 reconn/s: 0.00
SQL statistics:
    queries performed:
        read:                            595840
        write:                           170240
        other:                           85120
        total:                           851200
    transactions:                        42560  (141.79 per sec.)
    queries:                             851200 (2835.86 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.1547s
    total number of events:              42560

Latency (ms):
         min:                                    6.18
         avg:                                  141.02
         max:                                  687.11
         95th percentile:                      257.95
         sum:                              6001887.46

Threads fairness:
    events (avg/stddev):           2128.0000/14.78
    execution time (avg/stddev):   300.0944/0.03
```



##### 1.3 清理数据

使用如下命令清理数据

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300   --threads=20 --percentile=95 oltp_read_write cleanup
```



#### 2. 测试myisam 100w条记录的读写性能

##### 2.1 生成100w数据

使用

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20  oltp_read_write help
```

可以看到oltp_read_write脚本所支持的参数，如下所示：

```shell
[root@VM_0_5_centos ~]# sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20  oltp_read_write help
sysbench 1.0.20 (using bundled LuaJIT 2.1.0-beta2)

oltp_read_write options:
  --auto_inc[=on|off]           Use AUTO_INCREMENT column as Primary Key (for MySQL), or its alternatives in other DBMS. When disabled, use client-generated IDs [on]
  --create_secondary[=on|off]   Create a secondary index in addition to the PRIMARY KEY [on]
  --delete_inserts=N            Number of DELETE/INSERT combinations per transaction [1]
  --distinct_ranges=N           Number of SELECT DISTINCT queries per transaction [1]
  --index_updates=N             Number of UPDATE index queries per transaction [1]
  --mysql_storage_engine=STRING Storage engine, if MySQL is used [innodb]
  --non_index_updates=N         Number of UPDATE non-index queries per transaction [1]
  --order_ranges=N              Number of SELECT ORDER BY queries per transaction [1]
  --pgsql_variant=STRING        Use this PostgreSQL variant when running with the PostgreSQL driver. The only currently supported variant is 'redshift'. When enabled, create_secondary is automatically disabled, and delete_inserts is set to 0
  --point_selects=N             Number of point SELECT queries per transaction [10]
  --range_selects[=on|off]      Enable/disable all range SELECT queries [on]
  --range_size=N                Range size for range SELECT queries [100]
  --secondary[=on|off]          Use a secondary index in place of the PRIMARY KEY [off]
  --simple_ranges=N             Number of simple range SELECT queries per transaction [1]
  --skip_trx[=on|off]           Don't start explicit transactions and execute all queries in the AUTOCOMMIT mode [off]
  --sum_ranges=N                Number of SELECT SUM() queries per transaction [1]
  --table_size=N                Number of rows per table [10000]
  --tables=N                    Number of tables [1]
```

此处我们指定下数据库存储引擎为myisam，执行：

```
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20  oltp_read_write --mysql_storage_engine=myisam prepare
```

即可创建MyIsam的表。

##### 2.2 进行读写测试

###### 2.2.1 第一次执行100w次读写

执行命令

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write   --mysql_storage_engine=myisam run
```

测试结果如下：

```
......
SQL statistics:
    queries performed:
        read:                            722036
        write:                           206294
        other:                           103150
        total:                           1031480
    transactions:                        51574  (171.84 per sec.)
    queries:                             1031480 (3436.86 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.1224s
    total number of events:              51574

Latency (ms):
         min:                                    2.91
         avg:                                  116.36
         max:                                 2703.01
         95th percentile:                      153.02
         sum:                              6001171.45

Threads fairness:
    events (avg/stddev):           2578.7000/7.62
    execution time (avg/stddev):   300.0586/0.04
```



###### 2.2.2 第二次执行100w次读写

相同命令，再来一次：

```
......
SQL statistics:
    queries performed:
        read:                            697480
        write:                           199279
        other:                           99641
        total:                           996400
    transactions:                        49820  (165.95 per sec.)
    queries:                             996400 (3319.08 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.2032s
    total number of events:              49820

Latency (ms):
         min:                                    2.86
         avg:                                  120.45
         max:                                 3435.92
         95th percentile:                      176.73
         sum:                              6000916.19

Threads fairness:
    events (avg/stddev):           2491.0000/9.31
    execution time (avg/stddev):   300.0458/0.06
```





###### 2.2.3 第三次执行100w次读写  



```
······
SQL statistics:
    queries performed:
        read:                            680372
        write:                           194389
        other:                           97199
        total:                           971960
    transactions:                        48598  (161.84 per sec.)
    queries:                             971960 (3236.88 per sec.)
    ignored errors:                      0      (0.00 per sec.)
    reconnects:                          0      (0.00 per sec.)

General statistics:
    total time:                          300.2761s
    total number of events:              48598

Latency (ms):
         min:                                    2.88
         avg:                                  123.52
         max:                                 2916.81
         95th percentile:                      189.93
         sum:                              6002904.07

Threads fairness:
    events (avg/stddev):           2429.9000/10.74
    execution time (avg/stddev):   300.1452/0.07
```



##### 2.3 清理数据

```shell
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20  oltp_read_write cleanup
```



## 测试总结

|        | 压测指标   | InnoDB                                                       | MyISAM                                                       |
| ------ | ---------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 第一次 | 读、写次数 | 读：527282<br/>写：150652<br/>其他：75326<br/>事务：37663<br/>总计：753260 | 读：722036<br/>写：206294<br/>其他：103150<br/>总计：1031480 |
|        | 耗时       | 最小：7.13ms<br/>平均：159.35ms<br/>最大：873.88ms<br/>95分位数：292.60ms | 最小：2.91ms<br/>平均：116.36ms<br/>最大：2703.01ms<br/>95分位数：153.02ms |
|        | 吞吐量     | 1883.1500                                                    | 2578.7000                                                    |
| 第二次 | 读、写次数 | 读：574434<br/>写：164124<br/>其他：82062<br/>事务：41031<br/>总计：820620 | 读：697480<br/>写：199279<br/>其他：99641<br/>总计：996400   |
|        | 耗时       | 最小：6.62ms<br/>平均：146.24ms<br/>最大：709.59ms<br/>95分位数：262.64ms | 最小：2.86ms<br/>平均：120.45ms<br/>最大：3435.92ms<br/>95分位数：176.73ms |
|        | 吞吐量     | 2051.5500                                                    | 2491.0000                                                    |
| 第三次 | 读、写次数 | 读：595840<br/>写：170240<br/>其他：85120<br/>事务：42560<br/>总计：851200 | 读：680372<br/>写：194389<br/>其他：97199<br/>总计：971960   |
|        | 耗时       | 最小：6.18ms<br/>平均：141.02ms<br/>最大：687.11ms<br/>95分位数：257.95ms | 最小：2.88ms<br/>平均：123.52ms<br/>最大：2916.81ms<br/>95分位数：189.93ms |
|        | 吞吐量     | 2128.0000                                                    | 2429.9000                                                    |
|        |            |                                                              |                                                              |

### 分析

#### 纵向对比

经过预热之后，可以看到InnoDB的性能越来越好，每秒能处理2000多请求。不排除预热充分后能反超MyISAM的可能性，不过这个我就没继续多测试了。

MyISAM每秒能处理2500左右。

#### 横向对比

从目前做的测试来看，InnoDB在读写性能上还是不及MyISAM，不排除预热充分后能反超MyISAM的可能性，不过这个我就没继续多测试了。

## 其他

### 图形化

可以将sysbench压测结果输出日志、然后绘制成图片，这样看着更高大上、更加直观。可以参考这篇文章：https://yq.aliyun.com/articles/498680




## 遇到的问题
1. 本来想使用sysbench的预热功能，使用如下命令
```
sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write prewarm
```
但有报如下错误，时间有限，没仔细排查：
```
[root@VM_0_5_centos ~]# sysbench --db-driver=mysql --mysql-host=localhost --mysql-port=3306 --mysql-user=root --mysql-password=1qaz@WSX --mysql-db=sbtest --table_size=1000000 --tables=10 --events=0 --time=300  --threads=20 --percentile=95 --report-interval=10 oltp_read_write prewarm
sysbench 1.0.20 (using bundled LuaJIT 2.1.0-beta2)

Initializing worker threads...

Prewarming table sbtest6
Prewarming table sbtest7
Prewarming table sbtest9
Prewarming table sbtest8
Prewarming table sbtest1
Prewarming table sbtest5
Prewarming table sbtest3
Prewarming table sbtest10
Prewarming table sbtest2
Prewarming table sbtest4
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest2 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest3 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest4 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest5 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest6 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest7 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest8 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest9 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest10 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: mysql_drv_query() returned error 2013 (Lost connection to MySQL server during query) for query 'SELECT AVG(id) FROM (SELECT * FROM sbtest1 FORCE KEY (PRIMARY) LIMIT 1000000) t'
FATAL: `sysbench.cmdline.call_command' function failed: /usr/share/sysbench/oltp_common.lua:111: SQL error, errno = 2013, state = 'HY000': Lost connection to MySQL server during query
```

