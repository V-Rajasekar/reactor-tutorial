package com.learnreactiveprogramming.service;

import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_merge;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeSequentially;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeWith;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_mergeWith_mono;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_concatMap_Async;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_flatMap;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameFlux_flatMap_Async;
import static com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.nameMono_flatMapMany;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {

    @Test
    void testNameFlatMap() {
        Flux<String> names = Flux.fromIterable(List.of("alex", "ben", "martin"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > 3).map(s-> s)
              //  .map(s -> s.length() + "-" + s);
         .flatMap(s -> Flux.fromArray(s.split(""))).log();
        StepVerifier.create(names)
                .expectNext("A", "L", "E", "X", "M", "A","R", "T", "I","N")
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
    void testnameMono_flatMapMany() {
        Flux<String> input = nameMono_flatMapMany("alex");
        StepVerifier.create(input).expectNext("A","L","E","X") // Returning Flux<String>
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
