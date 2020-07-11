package com.wgdj.moviecatalog;

import java.net.URI;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;

import com.wgdj.moviecatalog.util.beansUtil.NullAwareBeanUtils;
import com.wgdj.moviecatalog.util.dataBase.DataBaseInitialization;
import com.wgdj.moviecatalog.zueira.Coiso;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class MovieCatalogApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext movieApp = SpringApplication.run(MovieCatalogApplication.class, args); 

		DataBaseInitialization dataBaseInit = movieApp.getBean(DataBaseInitialization.class);
		dataBaseInit.init();
		
		Flux<Coiso> flux = WebClient.create().get().uri(URI.create("https://httpbin.org/get")).retrieve()
				.bodyToFlux(Coiso.class);
		
		flux.subscribe(c -> { 
			System.out.println(c);
		});
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean lfb) {
		return new ValidatingMongoEventListener(lfb);
	}

	@Bean
	public BeanUtilsBean beansUtils(LocalValidatorFactoryBean lfb) {
		return new NullAwareBeanUtils();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
		return modelMapper;
	}

}
