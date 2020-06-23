package com.wgdj.moviecatalog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

//	@Autowired
//	private CollectionService collectionService;
//
//	@PostMapping("/collections")
//	@ResponseStatus(HttpStatus.CREATED)
//	public Mono<Collection> create(@Valid @RequestBody final Collection collection) {
//		return collectionService.save(collection).doOnNext(c -> log.debug("Salvar collection - {}", c));
//	}
//
//	@GetMapping("/collections/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	public Mono<Collection> findOne(@PathVariable final String id) {
//		return collectionService.findById(id).switchIfEmpty(Mono.error(new CollectionNaoEncontradaException("id:" + id.toString())))
//				.doOnNext(c -> log.debug("Buscar collection por id {}", c));
//	}
//	
//	@GetMapping("/collections/nome/{nome}")
//	@ResponseStatus(HttpStatus.OK)
//	public Flux<Collection> findByNome(@PathVariable(value = "nome") final String nome) {
//		return collectionService.findByNomeContaining(nome).switchIfEmpty(Mono.error(new CollectionNaoEncontradaException("nome:" + nome)))
//				.doOnNext(c -> log.debug("Buscar collection por nome {}", c));
//	}
//	
//	@GetMapping(path = "/collections/estado/{estado}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public Flux<Collection> findByEstado(@PathVariable(value = "estado") final String estado) {
//		return collectionService.findByEstado(estado).switchIfEmpty(Mono.error(new CollectionNaoEncontradaException("estado:" + estado)))
//				.doOnNext(c -> log.debug("Buscar collection por estado {}", c));
//	}
//	
//	@GetMapping(path = "/collections", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public Flux<Collection> findAll() {
//		return collectionService.findAll().doOnComplete(() -> log.debug("Buscar todas as collections."));
//	}

}