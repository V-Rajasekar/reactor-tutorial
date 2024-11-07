package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.core.publisher.Flux;
public class FluxBasicsTest {

    @Test
    public void testFooBarFluxFromValues() {
        FluxBasics fluxBasics = new FluxBasics();
     Flux<String> values =  fluxBasics.fooBarFluxFromValues();
        StepVerifier.create(values)
                .expectNextCount(2)
                .expectNext("foo", "bar")
                .verifyComplete();
    }
}
