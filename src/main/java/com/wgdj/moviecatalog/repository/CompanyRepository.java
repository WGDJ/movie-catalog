package com.wgdj.moviecatalog.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.wgdj.moviecatalog.model.Company;

import reactor.core.publisher.Flux;

public interface CompanyRepository extends ReactiveCrudRepository<Company, String> {

	Flux<Company> findAll(Example<Company> example);

}