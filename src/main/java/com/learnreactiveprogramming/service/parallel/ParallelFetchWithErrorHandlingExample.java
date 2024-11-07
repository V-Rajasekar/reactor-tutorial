package com.learnreactiveprogramming.service.parallel;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelFetchWithErrorHandlingExample {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ParallelFetchWithErrorHandlingExample.class);

    // Simulating a non-blocking call to the database
    private Mono<String> fetchDataFromDatabase() {
        return Mono.fromCallable(() -> {
                       // Log the start of the database call
                       logger.info("Fetching data from the database on thread: " + Thread.currentThread().getName());
                       // Simulate database call delay
                       Thread.sleep(1000);
                       if (Math.random() < 0.5) { // Simulate a failure half the time
                           throw new RuntimeException("Database is down");
                       }
                       return "Database Data";
                   }).subscribeOn(Schedulers.boundedElastic())
                   .onErrorResume(e -> {
                       logger.error("Error fetching data from database: " + e.getMessage());
                       return Mono.just("Default Database Data");
                   });
    }

    // Simulating a non-blocking call to an external API
    private Mono<String> fetchDataFromApi() {
        return Mono.fromCallable(() -> {
                       // Log the start of the API call
                       logger.info("Fetching data from the API on thread: " + Thread.currentThread().getName());
                       // Simulate API call delay
                       Thread.sleep(1000);
                       if (Math.random() < 0.5) { // Simulate a failure half the time
                           throw new RuntimeException("API call failed");
                       }
                       return "API Data";
                   }).subscribeOn(Schedulers.boundedElastic())
                   .onErrorResume(e -> {
                       logger.error("Error fetching data from API: " + e.getMessage());
                       return Mono.just("Default API Data");
                   });
    }

    // Main method to demonstrate parallel fetch, combination, and error handling
    public void fetchData() {
        // Fetch data from database and API in parallel
        Mono<String> databaseData = fetchDataFromDatabase();
        Mono<String> apiData = fetchDataFromApi();

        // Combine results and pass to the next operation in the stream pipeline
        Mono.zip(databaseData, apiData)
            .map(tuple -> {
                String combinedResult = "Combined Result: " + tuple.getT1() + " & " + tuple.getT2();
                // Log the combined result
                logger.info("Combined result calculated on thread: " + Thread.currentThread().getName());
                return combinedResult;
            })
            .subscribe(result -> logger.info("Received: " + result));

        // Keep the application running to see the output
        try {
            Thread.sleep(2000); // Ensure the main thread is alive to allow async operations to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ParallelFetchWithErrorHandlingExample example = new ParallelFetchWithErrorHandlingExample();
        example.fetchData();
    }
}

