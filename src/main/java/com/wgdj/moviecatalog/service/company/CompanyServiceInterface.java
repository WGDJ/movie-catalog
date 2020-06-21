package com.wgdj.moviecatalog.service.company;

import com.wgdj.moviecatalog.model.Company;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyServiceInterface{

  Mono<Company> save(Company company);
  
  Mono<Company> findById(String id);
  
  Flux<Company> findAll(Company company);
  
  Flux<Company> findAll();

}