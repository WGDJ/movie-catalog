package com.wgdj.moviecatalog.service.language;

import com.wgdj.moviecatalog.model.Language;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LanguageServiceInterface{

  Mono<Language> save(Language language);
  
  Mono<Language> findById(String id);
  
  Flux<Language> findAll(Language language);
  
  Flux<Language> findAll();

}