package com.auriga.webflux;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

@SpringBootTest
public class MonoFluxTest {
    /* Test - Mono */
    @Test
    public void testMono() {
        Function<String, String> mapper = String::toUpperCase;

        Mono<String> inMono = Mono.just("hello").log();
        Mono<String> outMono = inMono.map(mapper);

        /* Verifies if onSubscribe mono publisher, publishes uppercase element or not */
        StepVerifier.create(outMono)
                .expectNext("HELLO")
                .expectComplete()
                .verify();
    }

    /* Test - Flux Map */
    @Test
    public void testFluxMap() {
        Function<String, String> mapper = String::toUpperCase;

        Flux<String> inFlux = Flux.just("hello", "-", "world").log();
        Flux<String> outFlux = inFlux.map(mapper);

        /* Verifies if onSubscribe flux publisher, publishes uppercase elements or not */
        StepVerifier.create(outFlux)
                .expectNext("HELLO", "-", "WORLD")
                .expectComplete()
                .verify();
    }

    /* Test - Flux Flat Map */
    @Test
    public void testFluxFlatMap() {
        Function<String, Flux<String>> mapper = s->Flux.just(s.toUpperCase());

        Flux<String> inFlux = Flux.just("hello", "-", "world").log();
        Flux<String> outFlux = inFlux.flatMap(mapper);

        /* Verifies if onSubscribe flux publisher, publishes uppercase elements or not */
        StepVerifier.create(outFlux)
                .expectNext("HELLO", "-", "WORLD")
                .expectComplete()
                .verify();
    }

    /* Test - Flux Backpressure (Limiting) */
    @Test
    public void testFluxWhenRequestingChunks10() {
        Flux data = Flux.range(1, 50).log();

        /* Verifies if onSubscription when limiting flux publisher, do it publishes elements in chunks or not */
        StepVerifier.create(data)
                .expectSubscription()
                .thenRequest(10)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .thenRequest(10)
                .expectNext(11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
                .thenRequest(10)
                .expectNext(21, 22, 23, 24, 25, 26, 27, 28, 29, 30)
                .thenRequest(10)
                .expectNext(31, 32, 33, 34, 35, 36, 37, 38, 39, 40)
                .thenRequest(10)
                .expectNext(41, 42, 43, 44, 45, 46, 47, 48, 49, 50)
                .expectComplete()
                .verify();
    }

    /* Test - Flux Request Cancel  */
    @Test
    public void testFluxWhenRequestCancelled() {
        Flux<Integer> data = Flux.range(1, 10).log();

        /* Verifies if flux publish request is cancelled after 3 elements or not */
        StepVerifier.create(data)
                .expectNext(1, 2, 3)
                .thenCancel()
                .verify();
    }
}
