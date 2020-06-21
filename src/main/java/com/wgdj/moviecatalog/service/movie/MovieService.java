package com.wgdj.moviecatalog.service.movie;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.wgdj.moviecatalog.exceptions.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.util.NullAwareBeanUtilsBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieService implements MovieServiceInterface {

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public Mono<Movie> save(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public Mono<Movie> update(Movie movie) {

		try {
			Movie movieToUpdate = movieRepository.findById(movie.getId()).block();

			BeanUtilsBean notNullFilds = new NullAwareBeanUtilsBean();
			notNullFilds.copyProperties(movieToUpdate, movie);

			return movieRepository.save(movieToUpdate);

		} catch (Exception e) {
			throw new DatabaseObjectNotFoundException("Movie", movie.getId());
		}

	}

	@Override
	public Mono<Movie> findById(String id) {
		return movieRepository.findById(id);
	}

	@Override
	public Flux<Movie> findAll(Movie movie) {
		
//		final ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withMatcher("roles", match -> match.transform(source -> ((BasicDBList) source).iterator().next()).caseSensitive());
//
//        return movieRepository.findAll(Example.of(movie, matcher));
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
//				.withIgnorePaths("belongsToCollection", "genres", "productionCompanies")
				.withStringMatcher(StringMatcher.ENDING);
		Example<Movie> example = Example.of(movie, matcher);
		return movieRepository.findAll(example);
	}

	@Override
	public Flux<Movie> findAll() {
		return movieRepository.findAll();
	}

}