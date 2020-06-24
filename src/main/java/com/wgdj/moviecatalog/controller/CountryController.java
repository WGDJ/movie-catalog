package com.wgdj.moviecatalog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.service.country.CountryService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CountryController {

	@Autowired
	private CountryService countryService;

	@PostMapping("/countries")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Country> save(@Valid @RequestBody final Country country) {
		return countryService.save(country).doOnNext(c -> log.debug("Save country - {}", c));
	}
	
	@PutMapping("/countries")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Country> update(@RequestBody final Country country) {
		return countryService.update(country).doOnNext(c -> log.debug("Save country - {}", c));
	}

	@GetMapping("/countries/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Country> findById(@PathVariable final String id) {
		return countryService.findById(id).doOnNext(c -> log.debug("Find country by id - {}", c));
	}
	
	@GetMapping(path = "/countriesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Country> findAllByExample(@RequestBody final Country country) {
		return countryService.findAll(country).doOnNext(c -> log.debug("Find countries by example - {}", c));
	}
	
	@GetMapping(path = "/countries", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Country> findAll() {
		return countryService.findAll().doOnComplete(() -> log.debug("Find all countries - {}"));
	}

}