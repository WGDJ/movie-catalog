package com.wgdj.moviecatalog.service.movie;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.service.collection.CollectionService;
import com.wgdj.moviecatalog.service.company.CompanyService;
import com.wgdj.moviecatalog.service.country.CountryService;
import com.wgdj.moviecatalog.service.genre.GenreService;
import com.wgdj.moviecatalog.service.language.LanguageService;
import com.wgdj.moviecatalog.util.beansUtil.BeanUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieService implements MovieServiceInterface {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CollectionService collectionService;

	@Autowired
	private GenreService genreService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private BeanUtil beanUtils;

	@Override
	public Mono<Movie> save(Movie movie) {
		return movieRepository.save(movie).flatMap(composeMono());
	}

	@Override
	public Mono<Movie> update(final Movie movie) {
		return movieRepository.findById(movie.getId()).map(movieToUpdate -> {
			beanUtils.copyProperties(movieToUpdate, movie);
			return movieToUpdate;
		}).flatMap(movieRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Movie", movie.getId())))
		.flatMap(composeMono());
	}

	@Override
	public Mono<Movie> findById(final String id) {
		if (id == null) return Mono.empty();
		return movieRepository.findById(id).flatMap(composeMono());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Flux<Movie> findAll(final Movie movie) {

		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING)
				.withMatcher("genresIds", match -> match.transform(source -> 
					(Optional<Object>) Optional.of(((List) source.get()).iterator().next())
				).contains())
				.withMatcher("productionCompaniesIds", match -> match.transform(source -> 
					(Optional<Object>) Optional.of(((List) source.get()).iterator().next())
				).contains())
				.withMatcher("productionCountriesIds", match -> match.transform(source -> 
					(Optional<Object>) Optional.of(((List) source.get()).iterator().next())
				).contains())
				.withMatcher("spokenLanguagesIds", match -> match.transform(source -> 
					(Optional<Object>) Optional.of(((List) source.get()).iterator().next())
				).contains());

		return movieRepository.findAll(Example.of(movie, matcher)).flatMap(composeMono());

	}

	@Override
	public Flux<Movie> findAll() {
		return movieRepository.findAll().flatMap(composeMono());
	}
	
	private Function<? super Movie, ? extends Mono<? extends Movie>> composeMono() {
		return mov -> Mono.just(mov)
		.zipWith(collectionService.findById(mov.getBelongsToCollectionId()), (u, p) -> {
			u.setBelongsToCollection(p);
			return u;
		}).zipWith(genreService.findAllById(mov.getGenresIds()).collectList(), (u, p) -> {
			u.setGenres(p);
			return u;
		}).zipWith(companyService.findAllById(mov.getProductionCompaniesIds()).collectList(), (u, p) -> {
			u.setProductionCompanies(p);
			return u;
		}).zipWith(countryService.findAllById(mov.getProductionCountriesIds()).collectList(), (u, p) -> {
			u.setProductionCountries(p);
			return u;
		}).zipWith(languageService.findAllById(mov.getSpokenLanguagesIds()).collectList(), (u, p) -> {
			u.setSpokenLanguages(p);
			return u;
		});
	}

}
