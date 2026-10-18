package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class MovieReactiveServiceTest {
    private MovieInfoService movieInfoService = new MovieInfoService();

    private ReviewService reviewService = new ReviewService();
    @Test
    void getAllMovies() {
        MovieReactiveService movieRxService = new MovieReactiveService(movieInfoService, reviewService);
        var moviesFlux = movieRxService.getAllMovies();
        StepVerifier.create(moviesFlux)
              //  .expectNextCount(3)
                .assertNext(movie-> {
                    assertEquals("Batman Begins", movie.getMovie().getName());
                    assertEquals(2, movie.getReviewList().size());
                })
                .assertNext(movie-> {
                    assertEquals("The Dark Knight", movie.getMovie().getName());
                    assertEquals(2, movie.getReviewList().size());
                })
                .assertNext(movie-> {
                    assertEquals("Dark Knight Rises", movie.getMovie().getName());
                    assertEquals(2, movie.getReviewList().size());
                })
                    .verifyComplete();

    }
}