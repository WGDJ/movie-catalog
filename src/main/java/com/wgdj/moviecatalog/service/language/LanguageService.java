package com.wgdj.moviecatalog.service.language;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.repository.LanguageRepository;
import com.wgdj.moviecatalog.util.beansUtil.BeansUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LanguageService implements LanguageServiceInterface {

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private BeansUtil beanUtils;

	@Override
	public Mono<Language> save(final Language language) {
		return languageRepository.save(language);
	}

	@Override
	public Mono<Language> update(final Language language) {
		return languageRepository.findById(language.getId()).map(languageToUpdate -> {
			beanUtils.copyProperties(languageToUpdate, language);
			return languageToUpdate;
		}).flatMap(languageRepository::save)
		.switchIfEmpty(Mono.error(new DatabaseObjectNotFoundException("Language", language.getId())));
	}

	@Override
	public Mono<Language> findById(final String id) {
		return languageRepository.findById(id);
	}
	
	@Override
	public Flux<Language> findAllById(List<String> ids) {
		return languageRepository.findAllById(ids);
	}

	@Override
	public Flux<Language> findAll(final Language language) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Language> example = Example.of(language, matcher);
		return languageRepository.findAll(example);
	}

	@Override
	public Flux<Language> findAll() {
		return languageRepository.findAll();
	}

}
