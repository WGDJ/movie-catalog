package com.wgdj.moviecatalog.service.language;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.repository.LanguageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LanguageService implements LanguageServiceInterface {

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private BeanUtilsBean beanUtilsBean;

	@Override
	public Mono<Language> save(Language language) {
		return languageRepository.save(language);
	}

	@Override
	public Mono<Language> update(Language language) {
		try {
			Language languageToUpdate = languageRepository.findById(language.getId()).block();

			beanUtilsBean.copyProperties(languageToUpdate, language);

			return languageRepository.save(languageToUpdate);

		} catch (Exception e) {
			throw new DatabaseObjectNotFoundException("Language", language.getId());
		}
	}

	@Override
	public Mono<Language> findById(String id) {
		return languageRepository.findById(id);
	}

	@Override
	public Flux<Language> findAll(Language language) {
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
