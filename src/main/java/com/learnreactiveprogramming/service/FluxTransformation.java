package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.service.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxTransformation {

    // TODO Capitalize the user username, firstname and lastname
    //public final <R> Flux<R> flatMap(Function<? super T,? extends Publisher<? extends R>> mapper)
    //public final <V> Flux<V> map(Function<? super T,? extends V> mapper)
    Mono<User> capitalizeOne(Mono<User> mono) {
      return mono.flatMap(this::asyncCapitalizeUserMono).log();
     //return mono.map(this::asyncCapitalizeUser).log();
    }


    // TODO Capitalize the users username, firstName and lastName
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(um -> {
            System.out.println(um.toString());
         return   new User(um.getUsername().toUpperCase(), um.getFirstname().toUpperCase(),
                um.getLastname().toUpperCase());
        });
    }

//========================================================================================

    // TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
    Flux<User> asyncCapitalizeMany(Flux<User> user) {
         return user.flatMapSequential(usr -> asyncCapitalizeUserMono(usr)).log();
    }

    Mono<User> asyncCapitalizeUserMono(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
    }

    User asyncCapitalizeUser(User u) {
        return new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase());
    }

}
