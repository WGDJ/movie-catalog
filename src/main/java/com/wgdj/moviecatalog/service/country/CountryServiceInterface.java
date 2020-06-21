package com.wgdj.moviecatalog.service.country;

import com.wgdj.moviecatalog.model.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryServiceInterface{

  Mono<Country> save(Country country);
  
  Mono<Country> update(Country country);
  
  Mono<Country> findById(String id);
  
  Flux<Country> findAll(Country country);
  
  Flux<Country> findAll();

}