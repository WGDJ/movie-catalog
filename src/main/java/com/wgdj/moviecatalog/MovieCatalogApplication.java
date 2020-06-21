package com.wgdj.moviecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MovieCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
		
//		ConfigurableApplicationContext cac = SpringApplication.run(MovieCatalogApplication.class, args);
//		
//		Test t = cac.getBean(Test.class);
//		t.whenFindById_thenReturnCollection();
	}

}
