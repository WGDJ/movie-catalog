package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Movie;

import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveCrudRepository<Movie, String> {

	Flux<Movie> findAll(Example<Movie> example);

}