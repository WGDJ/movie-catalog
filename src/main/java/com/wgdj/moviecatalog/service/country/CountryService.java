package com.wgdj.moviecatalog.service.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.repository.CountryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CountryService implements CountryServiceInterface {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Mono<Country> save(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Mono<Country> findById(String id) {
		return countryRepository.findById(id);
	}

	@Override
	public Flux<Country> findAll(Country country) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.ENDING);
		Example<Country> example = Example.of(country, matcher);
		return countryRepository.findAll(example);
	}

	@Override
	public Flux<Country> findAll() {
		return countryRepository.findAll();
	}

}
