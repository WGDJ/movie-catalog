package com.wgdj.moviecatalog.service.genre;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.repository.GenreRepository;
import com.wgdj.moviecatalog.util.beansUtil.BeanUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GenreService implements GenreServiceInterface {

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private BeanUtil beanUtils;

	@Override
	public Mono<Genre> save(final Genre genre) {
		return genreRepository.save(genre);
	}

	@Override
	public Mono<Genre> update(final Genre genre) {
		return genreRepository.findById(genre.getId()).map(genreToUpdate -> {
			beanUtils.copyProperties(genreToUpdate, genre);
			return genreToUpdate;
		}).flatMap(genreRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Genre", genre.getId())));
	}

	@Override
	public Mono<Genre> findById(final String id) {
		if (id == null) return Mono.empty(); 
		return genreRepository.findById(id);
	}
	
	@Override
	public Flux<Genre> findAllById(final List<String> ids ) {
		if (ids == null) return Flux.empty();
		return genreRepository.findAllById(ids);
	}

	@Override
	public Flux<Genre> findAll(final Genre genre) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Genre> example = Example.of(genre, matcher);
		return genreRepository.findAll(example);
	}

	@Override
	public Flux<Genre> findAll() {
		return genreRepository.findAll();
	}

}
