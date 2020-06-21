package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Genre;

import reactor.core.publisher.Flux;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {

	Flux<Genre> findAll(Example<Genre> example);

}