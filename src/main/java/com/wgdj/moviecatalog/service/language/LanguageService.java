package com.wgdj.moviecatalog.service.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.repository.LanguageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LanguageService implements LanguageServiceInterface {

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public Mono<Language> save(Language language) {
		return languageRepository.save(language);
	}

	@Override
	public Mono<Language> findById(String id) {
		return languageRepository.findById(id);
	}

	@Override
	public Flux<Language> findAll(Language language) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.ENDING);
		Example<Language> example = Example.of(language, matcher);
		return languageRepository.findAll(example);
	}

	@Override
	public Flux<Language> findAll() {
		return languageRepository.findAll();
	}

}
