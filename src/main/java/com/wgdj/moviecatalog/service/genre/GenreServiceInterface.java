package com.wgdj.moviecatalog.service.genre;

import com.wgdj.moviecatalog.model.Genre;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreServiceInterface{

  Mono<Genre> save(Genre genre);
  
  Mono<Genre> findById(String id);
  
  Flux<Genre> findAll(Genre genre);
  
  Flux<Genre> findAll();

}