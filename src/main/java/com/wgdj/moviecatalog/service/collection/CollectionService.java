package com.wgdj.moviecatalog.service.collection;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CollectionService implements CollectionServiceInterface {

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private BeanUtilsBean beanUtilsBean;

	@Override
	public Mono<Collection> save(Collection collection) {
		return collectionRepository.save(collection);
	}

	@Override
	public Mono<Collection> update(Collection collection) {

		try {
			Collection collectionToUpdate = collectionRepository.findById(collection.getId()).block();

			beanUtilsBean.copyProperties(collectionToUpdate, collection);

			return collectionRepository.save(collectionToUpdate);

		} catch (Exception e) {
			throw new DatabaseObjectNotFoundException("Collection", collection.getId());
		}
	}

	@Override
	public Mono<Collection> findById(String id) {
		return collectionRepository.findById(id);
	}

	@Override
	public Flux<Collection> findAll(Collection collection) {
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
