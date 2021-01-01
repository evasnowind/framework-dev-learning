package com.prayerlaputa.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author chenglong.yu
 * created on 2020/12/31
 */
public class ReactorBasic {

    public static void main(String[] args) {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        //下面这个毫秒的接口目前的3.4.1版本找不到，回头可以看看啥原因
//        Flux.intervalMillis(1000).subscribe(System.out::println);
    }
}
