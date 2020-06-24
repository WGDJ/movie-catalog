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
import com.wgdj.moviecatalog.util.beansUtil.BeansUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GenreService implements GenreServiceInterface {

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private BeansUtil beanUtils;

	@Override
	public Mono<Genre> save(final Genre Genre) {
		return genreRepository.save(Genre);
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
		return genreRepository.findById(id);
	}
	
	@Override
	public Flux<Genre> findAllById(final List<String> ids ) {
		return genreRepository.findAllById(ids);
	}

	@Override
	public Flux<Genre> findAll(final Genre Genre) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Genre> example = Example.of(Genre, matcher);
		return genreRepository.findAll(example);
	}

	@Override
	public Flux<Genre> findAll() {
		return genreRepository.findAll();
	}

}
