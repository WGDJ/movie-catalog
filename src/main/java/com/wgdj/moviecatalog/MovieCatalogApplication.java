package com.wgdj.moviecatalog;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.wgdj.moviecatalog.util.beansUtil.NullAwareBeanUtilsBean;
import com.wgdj.moviecatalog.util.mongoCascade.CascadeSaveMongoEventListener;

@SpringBootApplication
public class MovieCatalogApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MovieCatalogApplication.class, args);

		ConfigurableApplicationContext cac = SpringApplication.run(MovieCatalogApplication.class, args);
		Teste t = cac.getBean(Teste.class);
		t.testar();
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
		return new NullAwareBeanUtilsBean();
	}

	@Bean
	public AbstractMongoEventListener<Object> mongoBeforeConvertEventListner() {
		return new CascadeSaveMongoEventListener();
	}

}
