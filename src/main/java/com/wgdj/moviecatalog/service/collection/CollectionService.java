package com.wgdj.moviecatalog.service.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CollectionService implements CollectionServiceInterface {

	@Autowired
	private CollectionRepository collectionRepository;

	@Override
	public Mono<Collection> save(Collection collection) {
		return collectionRepository.save(collection);
	}

	@Override
	public Mono<Collection> findById(String id) {
		return collectionRepository.findById(id);
	}

	@Override
	public Flux<Collection> findAll(Collection collection) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.ENDING);
		Example<Collection> example = Example.of(collection, matcher);
		return collectionRepository.findAll(example);
	}

	@Override
	public Flux<Collection> findAll() {
		return collectionRepository.findAll();
	}

}
