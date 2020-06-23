package com.wgdj.moviecatalog.service.company;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.repository.CompanyRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CompanyService implements CompanyServiceInterface {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BeanUtilsBean beanUtilsBean;

	@Override
	public Mono<Company> save(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Mono<Company> update(Company company) {
		try {
			Company companyToUpdate = companyRepository.findById(company.getId()).block();

			beanUtilsBean.copyProperties(companyToUpdate, company);

			return companyRepository.save(companyToUpdate);

		} catch (Exception e) {
			throw new DatabaseObjectNotFoundException("Company", company.getId());
		}
	}

	@Override
	public Mono<Company> findById(String id) {
		return companyRepository.findById(id);
	}

	@Override
	public Flux<Company> findAll(Company company) {
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
