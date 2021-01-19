package com.prayerlaputa.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

public class Debug {
    public static void main(String[] args) {
        Hooks.onOperator(providedHook -> providedHook.operatorStacktrace());
        Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);
    }
}
