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

import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.service.company.CompanyService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping("/companies")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Company> save(@Valid @RequestBody final Company company) {
		return companyService.save(company).doOnNext(c -> log.debug("Save company - {}", c));
	}
	
	@PutMapping("/companies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Company> update(@RequestBody final Company company) {
		return companyService.update(company).doOnNext(c -> log.debug("Save company - {}", c));
	}

	@GetMapping("/companies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Company> findById(@PathVariable final String id) {
		return companyService.findById(id).doOnNext(c -> log.debug("Find company by id - {}", c));
	}
	
	@GetMapping(path = "/companiesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Company> findAllByExample(@RequestBody final Company company) {
		return companyService.findAll(company).doOnNext(c -> log.debug("Find companies by example - {}", c));
	}
	
	@GetMapping(path = "/companies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Company> findAll() {
		return companyService.findAll().doOnComplete(() -> log.debug("Find all companies - {}"));
	}

}