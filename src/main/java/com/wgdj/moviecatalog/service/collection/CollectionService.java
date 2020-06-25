package com.wgdj.moviecatalog.service.collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;
import com.wgdj.moviecatalog.util.beansUtil.BeansUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CollectionService implements CollectionServiceInterface {

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private BeansUtil beanUtils;

	@Override
	public Mono<Collection> save(final Collection collection) {
		return collectionRepository.save(collection);
	}

	@Override
	public Mono<Collection> update(final Collection collection) {
		return collectionRepository.findById(collection.getId()).map(collectionToUpdate -> {
			beanUtils.copyProperties(collectionToUpdate, collection);
			return collectionToUpdate;
		}).flatMap(collectionRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Collection", collection.getId())));
	}

	@Override
	public Mono<Collection> findById(final String id) {
		if (id == null) return Mono.empty(); 
		return collectionRepository.findById(id);
	}
	
	@Override
	public Flux<Collection> findAllById(List<String> ids) {
		if (ids == null) return Flux.empty();
		return collectionRepository.findAllById(ids);
	}


	@Override
	public Flux<Collection> findAll(final Collection collection) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Collection> example = Example.of(collection, matcher);
		return collectionRepository.findAll(example);
	}

	@Override
	public Flux<Collection> findAll() {
		return collectionRepository.findAll();
	}

}
