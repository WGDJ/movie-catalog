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

import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.model.dtos.MovieByExampleDTO;
import com.wgdj.moviecatalog.model.dtos.MovieDTO;
import com.wgdj.moviecatalog.service.movie.MovieService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<MovieDTO> save(@RequestBody final MovieDTO movieDTO) {
		return movieService.save(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save movie - {}", o));
	}
	
	@PutMapping("/movies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<MovieDTO> update(@RequestBody final MovieDTO movieDTO) {
		return movieService.update(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save movie - {}", c));
	}

	@GetMapping("/movies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<MovieDTO> findById(@PathVariable final String id) {
		return movieService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find movie by id - {}", c));
	}
	
	@GetMapping(path = "/moviesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<MovieDTO> findAllByExample(@RequestBody final MovieByExampleDTO movieDTO) {
		return movieService.findAll(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find movies by example - {}", c));
	}
	
	@GetMapping(path = "/movies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<MovieDTO> findAll() {
		return movieService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all movies - {}"));
	}
	
	public MovieDTO convertToDto(Movie movie) {
		return modelMapper.map(movie, MovieDTO.class);
	}

	public Movie convertToEntity(MovieDTO movieDTO) {
		return modelMapper.map(movieDTO, Movie.class);
	}
	
	public Movie convertToEntity(MovieByExampleDTO movieDTO) {
		return modelMapper.map(movieDTO, Movie.class);
	}

}