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

import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.service.movie.MovieService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;

	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Movie> save(@Valid @RequestBody final Movie movie) {
		return movieService.save(movie).doOnNext(c -> log.debug("Save movie - {}", c));
	}
	
	@PutMapping("/movies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Movie> update(@RequestBody final Movie movie) {
		return movieService.update(movie).doOnNext(c -> log.debug("Save movie - {}", c));
	}

	@GetMapping("/movies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Movie> findById(@PathVariable final String id) {
		return movieService.findById(id).doOnNext(c -> log.debug("Find movie by id - {}", c));
	}
	
	@GetMapping(path = "/moviesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Movie> findAllByExample(@RequestBody final Movie movie) {
		return movieService.findAll(movie).doOnNext(c -> log.debug("Find movies by example - {}", c));
	}
	
	@GetMapping(path = "/movies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Movie> findAll() {
		return movieService.findAll().doOnComplete(() -> log.debug("Find all movies - {}"));
	}

}