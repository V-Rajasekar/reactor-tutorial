package com.learnreactiveprogramming.service;

import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_merge;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeSequentially;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeWith;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeWith_mono;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_concatMap_Async;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_flatMap;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_flatMap_Async;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

    @Test
    void testNameFlatMap() {
       var namesFlux = nameFlux_flatMap(3);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "M", "A","T", "T")
                .verifyComplete();
    }

    @Test
    void testNameFlatMapAsync() {
        var namesFlux = nameFlux_flatMap_Async(3);
        StepVerifier.create(namesFlux)
                    //.expectNext("A", "L", "E", "X", "M", "A","T", "T")
                .expectNextCount(8)
                    .verifyComplete();
    }

    @Test
    void testNameConcatMapAsync() {
        var namesFlux = nameFlux_concatMap_Async(3);
        StepVerifier.create(namesFlux)
                    .expectNext("A", "L", "E", "X", "M", "A","T", "T")
                   // .expectNextCount(8)
                    .verifyComplete();
    }

    @Test
    void testExplore_merge() {
        StepVerifier.create(explore_merge())
                    .expectNext("A", "D", "B", "E", "C", "F")
                    .verifyComplete();
    }

    @Test
    void testExplore_mergeSequentially() {
        StepVerifier.create(explore_mergeSequentially())
                    .expectNext("A", "B","C","D",  "E",  "F")
                    .verifyComplete();
    }

    @Test
    void testExplore_mergeWith_flux() {
        StepVerifier.create(explore_mergeWith())
                    .expectNext("A", "D", "B", "E", "C", "F")
                    .verifyComplete();
    }

    @Test
    void testExplore_mergeWith_mono() {
        StepVerifier.create(explore_mergeWith_mono())
                    .expectNext("A", "B")
                    .verifyComplete();
    }

}
