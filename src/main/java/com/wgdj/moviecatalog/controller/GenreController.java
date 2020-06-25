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

import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.dtos.GenreDTO;
import com.wgdj.moviecatalog.service.genre.GenreService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class GenreController {

	@Autowired
	private GenreService genreService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/genres")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<GenreDTO> save(@RequestBody final GenreDTO genreDTO) {
		return genreService.save(convertToEntity(genreDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save genre - {}", o));
	}
	
	@PutMapping("/genres")
	@ResponseStatus(HttpStatus.OK)
	public Mono<GenreDTO> update(@RequestBody final GenreDTO genreDTO) {
		return genreService.update(convertToEntity(genreDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save genre - {}", c));
	}

	@GetMapping("/genres/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<GenreDTO> findById(@PathVariable final String id) {
		return genreService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find genre by id - {}", c));
	}
	
	@GetMapping(path = "/genresByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<GenreDTO> findAllByExample(@RequestBody final GenreDTO genreDTO) {
		return genreService.findAll(convertToEntity(genreDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find genres by example - {}", c));
	}
	
	@GetMapping(path = "/genres", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<GenreDTO> findAll() {
		return genreService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all genres - {}"));
	}
	
	public GenreDTO convertToDto(Genre genre) {
		return modelMapper.map(genre, GenreDTO.class);
	}

	public Genre convertToEntity(GenreDTO genreDTO) {
		return modelMapper.map(genreDTO, Genre.class);
	}
}