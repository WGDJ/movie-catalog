package com.wgdj.moviecatalog;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.wgdj.moviecatalog.util.beansUtil.NullAwareBeanUtils;
import com.wgdj.moviecatalog.util.dataBase.DataBaseInitialization;

@SpringBootApplication
public class MovieCatalogApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext movieApp = SpringApplication.run(MovieCatalogApplication.class, args); 

		DataBaseInitialization dataBaseInit = movieApp.getBean(DataBaseInitialization.class);
		dataBaseInit.init();
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
