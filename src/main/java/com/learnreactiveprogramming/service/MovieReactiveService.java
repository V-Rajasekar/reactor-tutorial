package com.learnreactiveprogramming.service;

import java.util.List;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.MovieInfo;
import com.learnreactiveprogramming.domain.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MovieReactiveService {

    private MovieInfoService movieInfoService;

    private ReviewService reviewService;

    public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    /**
     * When building a reactive pipeline and if your operation is going to be returning another reactive type,
     * then we would use the flatmap operator.Here reviewService is returning another reactive type Mono, so we
     * are using flatMap
     */
    public Flux<Movie> getAllMovies() {
        Flux<MovieInfo> movieInfoFlux = movieInfoService.retrieveMoviesFlux();
        return movieInfoFlux.flatMap(
                movieInfo -> {
                    Mono<List<Review>> reviewsMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId())
                                                                  .collectList();
                    return reviewsMono
                            .map(reviewList -> new Movie(movieInfo, reviewList)).log();
                });
    }
}
