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

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.service.collection.CollectionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CollectionController {

	@Autowired
	private CollectionService collectionService;

	@PostMapping("/collections")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Collection> save(@Valid @RequestBody final Collection collection) {
		return collectionService.save(collection).doOnNext(c -> log.debug("Save collection - {}", c));
	}
	
	@PutMapping("/collections")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Collection> update(@RequestBody final Collection collection) {
		return collectionService.update(collection).doOnNext(c -> log.debug("Save collection - {}", c));
	}

	@GetMapping("/collections/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Collection> findById(@PathVariable final String id) {
		return collectionService.findById(id).doOnNext(c -> log.debug("Find collection by id - {}", c));
	}
	
	@GetMapping(path = "/collectionsByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Collection> findAllByExample(@RequestBody final Collection collection) {
		return collectionService.findAll(collection).doOnNext(c -> log.debug("Find collections by example - {}", c));
	}
	
	@GetMapping(path = "/collections", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Collection> findAll() {
		return collectionService.findAll().doOnComplete(() -> log.debug("Find all collections - {}"));
	}

}