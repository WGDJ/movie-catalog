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

import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.service.genre.GenreService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class GenreController {

	@Autowired
	private GenreService genreService;

	@PostMapping("/genres")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Genre> save(@Valid @RequestBody final Genre genre) {
		return genreService.save(genre).doOnNext(c -> log.debug("Save genre - {}", c));
	}
	
	@PutMapping("/genres")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Genre> update(@RequestBody final Genre genre) {
		return genreService.update(genre).doOnNext(c -> log.debug("Save genre - {}", c));
	}

	@GetMapping("/genres/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Genre> findById(@PathVariable final String id) {
		return genreService.findById(id).doOnNext(c -> log.debug("Find genre by id - {}", c));
	}
	
	@GetMapping(path = "/genresByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Genre> findAllByExample(@RequestBody final Genre genre) {
		return genreService.findAll(genre).doOnNext(c -> log.debug("Find genres by example - {}", c));
	}
	
	@GetMapping(path = "/genres", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Genre> findAll() {
		return genreService.findAll().doOnComplete(() -> log.debug("Find all genres - {}"));
	}

}