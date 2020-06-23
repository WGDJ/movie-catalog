package com.wgdj.moviecatalog;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.wgdj.moviecatalog.util.beansUtil.NullAwareBeanUtils;
import com.wgdj.moviecatalog.util.mongoCascade.CascadeSaveMongoEventListener;

@SpringBootApplication
public class MovieCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
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
	public AbstractMongoEventListener<Object> mongoBeforeConvertEventListner() {
		return new CascadeSaveMongoEventListener();
	}

}
