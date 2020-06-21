package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Collection;

import reactor.core.publisher.Flux;

public interface CollectionRepository extends ReactiveCrudRepository<Collection, String> {

	Flux<Collection> findAll(Example<Collection> example);

}