package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;

public class ReactiveStreamException {

    public Flux<String> nameFluxException() {

      return  Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D")).log();
    }

    public Flux<String> onErrorReturn() {
        return  Flux.just("A", "B", "C")
                    .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .onErrorReturn("D");
    }
}
