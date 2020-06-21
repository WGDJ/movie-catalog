package com.wgdj.moviecatalog.service.movie;

import com.wgdj.moviecatalog.model.Movie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieServiceInterface{

  Mono<Movie> save(Movie movie);
  
  Mono<Movie> update(Movie movie);
  
  Mono<Movie> findById(String id);
  
  Flux<Movie> findAll(Movie movie);
  
  Flux<Movie> findAll();

}