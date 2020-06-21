package com.wgdj.moviecatalog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;
import com.wgdj.moviecatalog.service.collection.CollectionServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persitence testes for entity Collection")
public class MovieTest {

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private CollectionServiceInterface collectionService;

	@DisplayName("Test persist with requred filds not blank")
	@DirtiesContext
	@Test
	public void whenPersistWithRequredFildsNotBlank_thenReturnCollectionFindById() {
		Collection collection = createCollection("Mad Max Collection");
		collection = collectionService.save(collection).block();

		Collection found = collectionService.findById(collection.getId()).block();

		assertThat(found.getName()).isEqualTo(collection.getName());
	}
	
	@DisplayName("Test persist with requred filds blank")
	@DirtiesContext
	@Test
	public void whenPersistWithoutRequredFildsNotBlank_thenThrowsException() {
		Collection collection = createCollection("");
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			collectionService.save(collection).block();
	    });
		
		assertThat(exception.getMessage()).contains("Collection name is required.");

	}

	@DisplayName("Test FindAll (for example) Collection")
	@DirtiesContext
	@Test
	public void whenFindByExample_thenReturn2Collections() {
		List<Collection> collections = new ArrayList<Collection>();
		collections.add(createCollection("Mad Max Collection"));
		collections.add(createCollection("Star Wars Collection"));
		collections.add(createCollection("Hannibal Lecter"));
		collections.add(createCollection("Mad Man"));
		collectionRepository.saveAll(collections).blockLast();
		
		Collection collectionExample = new Collection();
		collectionExample.setName("Mad");

		List<Collection> found = collectionService.findAll(collectionExample).collectList().block();

		assertThat(found.size()).isEqualTo(2);
	}
	
	@DisplayName("Test ByExample (for example) Collection")
	@DirtiesContext
	@Test
	public void whenFindAll_thenReturn4Collections() {
		List<Collection> collections = new ArrayList<Collection>();
		collections.add(createCollection("Mad Max Collection"));
		collections.add(createCollection("Star Wars Collection"));
		collections.add(createCollection("Hannibal Lecter"));
		collections.add(createCollection("Mad Man"));
		collectionRepository.saveAll(collections).blockLast();
		
		Collection collectionExample = new Collection();
		collectionExample.setName("Mad");

		List<Collection> found = collectionService.findAll(collectionExample).collectList().block();

		assertThat(found.size()).isEqualTo(2);
	}
	
	private Collection createCollection(String name) {
		Collection collection = new Collection();
		collection.setName(name);
		return collection;
	}

}
