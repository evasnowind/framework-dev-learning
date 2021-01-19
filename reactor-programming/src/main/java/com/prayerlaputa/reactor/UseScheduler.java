package com.prayerlaputa.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class UseScheduler {
    public static void main(String[] args) {
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
        .publishOn(Schedulers.single())
        .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
        .publishOn(Schedulers.elastic())
        .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
        .subscribeOn(Schedulers.parallel())
        .toStream()
        .forEach(System.out::println);
    }
}
