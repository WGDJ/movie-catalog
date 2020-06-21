package com.wgdj.moviecatalog.service.collection;

import com.wgdj.moviecatalog.model.Collection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CollectionServiceInterface{

  Mono<Collection> save(Collection collection);
  
  Mono<Collection> update(Collection collection);
  
  Mono<Collection> findById(String id);
  
  Flux<Collection> findAll(Collection collection);
  
  Flux<Collection> findAll();

}