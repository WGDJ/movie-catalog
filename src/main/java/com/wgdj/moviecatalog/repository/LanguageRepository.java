package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Language;

import reactor.core.publisher.Flux;

public interface LanguageRepository extends ReactiveCrudRepository<Language, String> {

	Flux<Language> findAll(Example<Language> example);

}