package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.service.model.User;
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

    @Test
    public void testAsyncCapitalizeMany() {
        Flux<User> users = Flux.just(User.SKYLER, User.JESSE, User.WALTER, User.SAUL);
        FluxTransformation fxTransform = new FluxTransformation();
        StepVerifier.create( fxTransform.asyncCapitalizeMany(users))
                .expectNext(User.SKYLERC).verifyComplete();
    }
}
