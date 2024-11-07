package com.learnreactiveprogramming.service.parallel;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Result of this call
 * 07:54:48.555 [main] DEBUG reactor.util.Loggers$LoggerFactory - Using Slf4j logging framework
 * 07:54:48.636 [boundedElastic-1] INFO com.learnreactiveprogramming.service.parallel.ParallelFetchExample - Fetching data from the database on thread: boundedElastic-1
 * 07:54:48.636 [boundedElastic-2] INFO com.learnreactiveprogramming.service.parallel.ParallelFetchExample - Fetching data from the API on thread: boundedElastic-2
 * 07:54:49.647 [boundedElastic-2] INFO com.learnreactiveprogramming.service.parallel.ParallelFetchExample - Combined result calculated on thread: boundedElastic-2
 * 07:54:49.649 [boundedElastic-2] INFO com.learnreactiveprogramming.service.parallel.ParallelFetchExample - Received: Combined Result: Database Data & API Data
 */
public class ParallelFetchExample {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ParallelFetchExample.class);

    // Simulating a non-blocking call to the database
    private Mono<String> fetchDataFromDatabase() {
        return Mono.fromCallable(() -> {
            // Log the start of the database call
            logger.info("Fetching data from the database on thread: " + Thread.currentThread().getName());
            // Simulate database call delay
            Thread.sleep(1000);
            return "Database Data";
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // Simulating a non-blocking call to an external API
    private Mono<String> fetchDataFromApi() {
        return Mono.fromCallable(() -> {
            // Log the start of the API call
            logger.info("Fetching data from the API on thread: " + Thread.currentThread().getName());
            // Simulate API call delay
            Thread.sleep(1000);
            return "API Data";
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // Main method to demonstrate parallel fetch and combination
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
        ParallelFetchExample example = new ParallelFetchExample();
        example.fetchData();
    }
}

