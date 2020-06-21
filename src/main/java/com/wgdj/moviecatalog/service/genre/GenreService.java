package com.wgdj.moviecatalog.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.repository.GenreRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GenreService implements GenreServiceInterface {

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public Mono<Genre> save(Genre Genre) {
		return genreRepository.save(Genre);
	}

	@Override
	public Mono<Genre> findById(String id) {
		return genreRepository.findById(id);
	}

	@Override
	public Flux<Genre> findAll(Genre Genre) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.ENDING);
		Example<Genre> example = Example.of(Genre, matcher);
		return genreRepository.findAll(example);
	}

	@Override
	public Flux<Genre> findAll() {
		return genreRepository.findAll();
	}

}
