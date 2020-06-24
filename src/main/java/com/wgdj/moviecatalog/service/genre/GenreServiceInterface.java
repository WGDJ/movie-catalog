package com.wgdj.moviecatalog.service.genre;

import java.util.List;

import com.wgdj.moviecatalog.model.Genre;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreServiceInterface{

  Mono<Genre> save(Genre genre);
  
  Mono<Genre> update(Genre genre);
  
  Mono<Genre> findById(String id);
  
  Flux<Genre> findAllById(List<String> ids);
  
  Flux<Genre> findAll(Genre genre);
  
  Flux<Genre> findAll();


}