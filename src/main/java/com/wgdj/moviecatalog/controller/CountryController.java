package com.wgdj.moviecatalog.controller;

import org.modelmapper.ModelMapper;
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
import com.wgdj.moviecatalog.model.dtos.CountryDTO;
import com.wgdj.moviecatalog.service.country.CountryService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/countries")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CountryDTO> save(@RequestBody final CountryDTO countryDTO) {
		return countryService.save(convertToEntity(countryDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save country - {}", o));
	}
	
	@PutMapping("/countries")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CountryDTO> update(@RequestBody final CountryDTO countryDTO) {
		return countryService.update(convertToEntity(countryDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save country - {}", c));
	}

	@GetMapping("/countries/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CountryDTO> findById(@PathVariable final String id) {
		return countryService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find country by id - {}", c));
	}
	
	@PostMapping(path = "/countriesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CountryDTO> findAllByExample(@RequestBody final CountryDTO countryDTO) {
		return countryService.findAll(convertToEntity(countryDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find countries by example - {}", c));
	}
	
	@GetMapping(path = "/countries", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CountryDTO> findAll() {
		return countryService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all countries - {}"));
	}
	
	public CountryDTO convertToDto(Country country) {
		return modelMapper.map(country, CountryDTO.class);
	}

	public Country convertToEntity(CountryDTO countryDTO) {
		return modelMapper.map(countryDTO, Country.class);
	}

}