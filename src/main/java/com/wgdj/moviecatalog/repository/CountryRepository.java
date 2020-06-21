package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Country;

import reactor.core.publisher.Flux;

public interface CountryRepository extends ReactiveCrudRepository<Country, String> {

	Flux<Country> findAll(Example<Country> example);

}