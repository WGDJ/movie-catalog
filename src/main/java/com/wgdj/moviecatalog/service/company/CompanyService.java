package com.wgdj.moviecatalog.service.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.repository.CompanyRepository;
import com.wgdj.moviecatalog.util.beansUtil.BeansUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CompanyService implements CompanyServiceInterface {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BeansUtil beanUtils;
	
	@Override
	public Mono<Company> save(final Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Mono<Company> update(final Company company) {
		return companyRepository.findById(company.getId()).map(companyToUpdate -> {
			beanUtils.copyProperties(companyToUpdate, company);
			return companyToUpdate;
		}).flatMap(companyRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Company", company.getId())));
	}

	@Override
	public Mono<Company> findById(final String id) {
		return companyRepository.findById(id);
	}
	
	@Override
	public Flux<Company> findAllById(List<String> ids) {
		return companyRepository.findAllById(ids);
	}

	@Override
	public Flux<Company> findAll(final Company company) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Company> example = Example.of(company, matcher);
		return companyRepository.findAll(example);
	}

	@Override
	public Flux<Company> findAll() {
		return companyRepository.findAll();
	}

}
