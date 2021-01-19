package com.prayerlaputa.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactorTest {
//    @Test
//    public void simpleTest() {
//        StepVerifier.create(Flux.just("a", "b"))
//                .expectNext("a")
//                .expectNext("b")
//                .verifyComplete();
//    }
//
//    @Test
//    public void testWithTime() {
//        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
//                .expectSubscription()
//                .expectNoEvent(Duration.ofHours(4))
//                .expectNext(0L)
//                .thenAwait(Duration.ofDays(1))
//                .expectNext(1L)
//                .verifyComplete();
//    }
//
//    @Test
//    public void withTestPublisher() {
//        final TestPublisher<String> testPublisher = TestPublisher.create();
//        testPublisher.next("a");
//        testPublisher.next("b");
//        testPublisher.complete();
//
//        StepVerifier.create(testPublisher)
//                .expectNext("a")
//                .expectNext("b")
//                .expectComplete();
//    }
}
