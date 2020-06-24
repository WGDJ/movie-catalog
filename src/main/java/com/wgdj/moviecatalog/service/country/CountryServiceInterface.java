package com.wgdj.moviecatalog.service.country;

import java.util.List;

import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryServiceInterface{

  Mono<Country> save(Country country);
  
  Mono<Country> update(Country country);
  
  Mono<Country> findById(String id);
  
  Flux<Country> findAllById(List<String> ids);
  
  Flux<Country> findAll(Country country);
  
  Flux<Country> findAll();

}