package com.wgdj.moviecatalog.service.country;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.repository.CountryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CountryService implements CountryServiceInterface {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private BeanUtilsBean beanUtilsBean;

	@Override
	public Mono<Country> save(final Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Mono<Country> update(final Country country) {
		try {
			Country countryToUpdate = countryRepository.findById(country.getId()).block();

			beanUtilsBean.copyProperties(countryToUpdate, country);

			return countryRepository.save(countryToUpdate);

		} catch (Exception e) {
			throw new DatabaseObjectNotFoundException("Country", country.getId());
		}
	}

	@Override
	public Mono<Country> findById(final String id) {
		return countryRepository.findById(id);
	}

	@Override
	public Flux<Country> findAll(final Country country) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Country> example = Example.of(country, matcher);
		return countryRepository.findAll(example);
	}

	@Override
	public Flux<Country> findAll() {
		return countryRepository.findAll();
	}

}
