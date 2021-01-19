package com.prayerlaputa.reactor;

import reactor.core.publisher.Mono;

import java.util.Optional;

public class MonoCreator {
    public static void main(String[] args) {
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }
}
