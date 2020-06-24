package com.wgdj.moviecatalog.service.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.util.beansUtil.BeansUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieService implements MovieServiceInterface {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private BeansUtil beanUtils;

	@Override
	public Mono<Movie> save(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public Mono<Movie> update(final Movie movie) {
		return movieRepository.findById(movie.getId()).map(movieToUpdate -> {
			beanUtils.copyProperties(movieToUpdate, movie);
			return movieToUpdate;
		}).flatMap(movieRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Movie", movie.getId())));
	}

	@Override
	public Mono<Movie> findById(final String id) {
		return movieRepository.findById(id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Flux<Movie> findAll(final Movie movie) {

		ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withStringMatcher(StringMatcher.STARTING)
            .withMatcher("genres", match -> match.transform(source -> {
            	return (Optional<Object>) Optional.of(((List) source.get()).iterator().next());
            }).contains())
            .withMatcher("productionCompanies", match -> match.transform(source -> {
            	return (Optional<Object>) Optional.of(((List) source.get()).iterator().next());
            }).contains())
            .withMatcher("productionCountries", match -> match.transform(source -> {
            	return (Optional<Object>) Optional.of(((List) source.get()).iterator().next());
            }).contains())
            .withMatcher("spokenLanguages", match -> match.transform(source -> {
            	return (Optional<Object>) Optional.of(((List) source.get()).iterator().next());
            }).contains());
		
		Example<Movie> example = Example.of(movie, matcher);
		return movieRepository.findAll(example);
	}

	@Override
	public Flux<Movie> findAll() {
		return movieRepository.findAll();
	}

}
