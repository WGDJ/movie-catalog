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

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.dtos.CollectionDTO;
import com.wgdj.moviecatalog.service.collection.CollectionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CollectionController {

	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/collections")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CollectionDTO> save(@RequestBody final CollectionDTO collectionDTO) {
		return collectionService.save(convertToEntity(collectionDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save collection - {}", o));
	}
	
	@PutMapping("/collections")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CollectionDTO> update(@RequestBody final CollectionDTO collectionDTO) {
		return collectionService.update(convertToEntity(collectionDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save collection - {}", c));
	}

	@GetMapping("/collections/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CollectionDTO> findById(@PathVariable final String id) {
		return collectionService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find collection by id - {}", c));
	}
	
	@GetMapping(path = "/collectionsByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CollectionDTO> findAllByExample(@RequestBody final CollectionDTO collectionDTO) {
		return collectionService.findAll(convertToEntity(collectionDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find collections by example - {}", c));
	}
	
	@GetMapping(path = "/collections", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CollectionDTO> findAll() {
		return collectionService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all collections - {}"));
	}
	
	public CollectionDTO convertToDto(Collection collection) {
		return modelMapper.map(collection, CollectionDTO.class);
	}

	public Collection convertToEntity(CollectionDTO collectionDTO) {
		return modelMapper.map(collectionDTO, Collection.class);
	}

}