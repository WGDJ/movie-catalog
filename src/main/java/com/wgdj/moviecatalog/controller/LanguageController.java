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

import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.dtos.LanguageDTO;
import com.wgdj.moviecatalog.service.language.LanguageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class LanguageController {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/languages")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<LanguageDTO> save(@RequestBody final LanguageDTO languageDTO) {
		return languageService.save(convertToEntity(languageDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save language - {}", o));
	}
	
	@PutMapping("/languages")
	@ResponseStatus(HttpStatus.OK)
	public Mono<LanguageDTO> update(@RequestBody final LanguageDTO languageDTO) {
		return languageService.update(convertToEntity(languageDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save language - {}", c));
	}

	@GetMapping("/languages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<LanguageDTO> findById(@PathVariable final String id) {
		return languageService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find language by id - {}", c));
	}
	
	@GetMapping(path = "/languagesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<LanguageDTO> findAllByExample(@RequestBody final LanguageDTO languageDTO) {
		return languageService.findAll(convertToEntity(languageDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find languages by example - {}", c));
	}
	
	@GetMapping(path = "/languages", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<LanguageDTO> findAll() {
		return languageService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all languages - {}"));
	}
	
	public LanguageDTO convertToDto(Language language) {
		return modelMapper.map(language, LanguageDTO.class);
	}

	public Language convertToEntity(LanguageDTO languageDTO) {
		return modelMapper.map(languageDTO, Language.class);
	}

}