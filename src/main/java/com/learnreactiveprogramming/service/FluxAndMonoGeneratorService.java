package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

    public static void javaStreamOperation(int strLength) {
        var nameMapLength = Stream.of("alex", "ben", "marting")
                 .map(String::toUpperCase)
                 .filter(s -> s.length() > strLength)
                 .collect(Collectors.toMap(s -> s.length(), s -> s));

        System.out.println("NameMap" + nameMapLength);
    }

    public static void nameFluxMap(int strLength) {
        Flux.fromIterable(List.of("alex", "ben", "martin"))
            .map(String::toUpperCase)
            .filter(s -> s.length() > strLength)
            .map(s -> s.length() + "-" + s)
            .log();
    }

    public static  Flux<String> nameFlux_flatMap(int strLength) {
      Flux<String> result =  Flux.fromIterable(List.of("alex", "ben", "matt"))
            .map(String::toUpperCase)
            .filter(s -> s.length() > strLength)
            .flatMap(FluxAndMonoGeneratorService::splitStringToChar).log();

        System.out.printf("Result FlatMap: " + result);
        return result;

    }

    public static  Flux<String> nameFlux_flatMap_Async(int strLength) {
        Flux<String> result =  Flux.fromIterable(List.of("alex", "ben", "matt"))
                                   .map(String::toUpperCase)
                                   .filter(s -> s.length() > strLength)
                                   .flatMap(FluxAndMonoGeneratorService::splitString_withDelay).log();

        System.out.printf("Result FlatMap: " + result);
        return result;

    }

    //Use concatMap() if ordering matters
    public static  Flux<String> nameFlux_concatMap_Async(int strLength) {
        Flux<String> result =  Flux.fromIterable(List.of("alex", "ben", "matt"))
                                   .map(String::toUpperCase)
                                   .filter(s -> s.length() > strLength)
                                   .concatMap(FluxAndMonoGeneratorService::splitString_withDelay).log();

        System.out.printf("Result FlatMap: " + result);
        return result;

    }

    private static Flux<String> splitStringToChar(String s) {
       return Flux.fromArray(s.split(""));
    }

    private static Flux<String> splitString_withDelay(String s) {
        int delay = 1000;
        return Flux.fromArray(s.split("")).delayElements(Duration.ofMillis(delay));
    }


    public static final Flux<String> explore_concat() {
        Flux<String> flux1 = Flux.just("A", "B", "C");

        Flux<String> flux2 = Flux.just("D", "E", "F");
        return Flux.concat(flux1, flux2).log();
    }

    public static final Flux<String> explore_merge() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));

        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return Flux.merge(flux1, flux2).log();
    }

    public static final Flux<String> explore_mergeWith() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));

        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return flux1.mergeWith(flux2).log();
    }

    public static final Flux<String> explore_mergeWith_mono() {
        Mono<String> mono1 = Mono.just("A");

        Mono<String> mono2 = Mono.just("B");
        return mono1.mergeWith(mono2);
    }

    public static final Flux<String> explore_mergeSequentially() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));

        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(flux1, flux2).log();
    }

    public static final Flux<String> explore_zipFlux() {
        Flux<String> fluxa = Flux.just("A", "B", "C");
        Flux<String> fluxb = Flux.just("D", "E", "F");
        return Flux.zip(fluxa, fluxb, (first, second) -> first + second); // AD, BE, CF
    }

    public static final Mono<String> explore_zipWith_mono() {
        Mono<String> monoA = Mono.just("A");
        Mono<String> monoB = Mono.just("B");
        return monoA.zipWith(monoB).map(t -> t.getT1() + t.getT2()); // AB
    }

    public static void main(String[] args) {
        nameFluxMap(3);

        nameFlux_flatMap(3);
    }
}







