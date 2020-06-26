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

import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.dtos.CompanyDTO;
import com.wgdj.moviecatalog.service.company.CompanyService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ModelMapper modelMapper; 

	@PostMapping("/companies")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<CompanyDTO> save(@RequestBody final CompanyDTO companyDTO) {
		return companyService.save(convertToEntity(companyDTO))
				.map(this::convertToDto)
				.doOnNext(o -> log.debug("Save company - {}", o));
	}
	
	@PutMapping("/companies")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CompanyDTO> update(@RequestBody final CompanyDTO companyDTO) {
		return companyService.update(convertToEntity(companyDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Save company - {}", c));
	}

	@GetMapping("/companies/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<CompanyDTO> findById(@PathVariable final String id) {
		return companyService.findById(id)
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find company by id - {}", c));
	}
	
	@PostMapping(path = "/companiesByExample", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CompanyDTO> findAllByExample(@RequestBody final CompanyDTO companyDTO) {
		return companyService.findAll(convertToEntity(companyDTO))
				.map(this::convertToDto)
				.doOnNext(c -> log.debug("Find companies by example - {}", c));
	}
	
	@GetMapping(path = "/companies", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<CompanyDTO> findAll() {
		return companyService.findAll()
				.map(this::convertToDto)
				.doOnComplete(() -> log.debug("Find all companies - {}"));
	}
	
	public CompanyDTO convertToDto(Company company) {
		return modelMapper.map(company, CompanyDTO.class);
	}

	public Company convertToEntity(CompanyDTO companyDTO) {
		return modelMapper.map(companyDTO, Company.class);
	}

}