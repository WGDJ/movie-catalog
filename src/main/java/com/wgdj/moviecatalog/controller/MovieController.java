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
import com.wgdj.moviecatalog.model.dtos.MovieInDTO;
import com.wgdj.moviecatalog.model.dtos.MovieOutDTO;
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
	public Mono<MovieOutDTO> save(@RequestBody final MovieInDTO movieDTO) {
		return movieService.save(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save movie - {}", o));
	}
	
	@PutMapping("/movies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<MovieOutDTO> update(@RequestBody final MovieInDTO movieDTO) {
		return movieService.update(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save movie - {}", c));
	}

	@GetMapping("/movies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<MovieOutDTO> findById(@PathVariable final String id) {
		return movieService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find movie by id - {}", c));
	}
	
	@PostMapping(path = "/moviesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<MovieOutDTO> findAllByExample(@RequestBody final MovieInDTO movieDTO) {
		return movieService.findAll(convertToEntity(movieDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find movies by example - {}", c));
	}
	
	@GetMapping(path = "/movies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<MovieOutDTO> findAll() {
		return movieService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all movies - {}"));
	}
	
	public MovieOutDTO convertToDto(Movie movie) {
		return modelMapper.map(movie, MovieOutDTO.class);
	}

	public Movie convertToEntity(MovieInDTO movieDTO) {
		return modelMapper.map(movieDTO, Movie.class);
	}

}