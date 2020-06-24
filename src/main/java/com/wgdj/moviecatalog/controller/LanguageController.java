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

import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.service.language.LanguageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class LanguageController {

	@Autowired
	private LanguageService languageService;

	@PostMapping("/languages")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Language> save(@Valid @RequestBody final Language language) {
		return languageService.save(language).doOnNext(c -> log.debug("Save language - {}", c));
	}
	
	@PutMapping("/languages")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Language> update(@RequestBody final Language language) {
		return languageService.update(language).doOnNext(c -> log.debug("Save language - {}", c));
	}

	@GetMapping("/languages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Language> findById(@PathVariable final String id) {
		return languageService.findById(id).doOnNext(c -> log.debug("Find language by id - {}", c));
	}
	
	@GetMapping(path = "/languagesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Language> findAllByExample(@RequestBody final Language language) {
		return languageService.findAll(language).doOnNext(c -> log.debug("Find languages by example - {}", c));
	}
	
	@GetMapping(path = "/languages", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Language> findAll() {
		return languageService.findAll().doOnComplete(() -> log.debug("Find all languages - {}"));
	}

}