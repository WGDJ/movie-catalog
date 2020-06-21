package com.wgdj.moviecatalog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wgdj.moviecatalog.exceptions.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;
import com.wgdj.moviecatalog.service.collection.CollectionServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persitence testes for entity Collection")
public class CollectionTest {

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private CollectionServiceInterface collectionService;

	@Before
	public void init() {
		collectionRepository.deleteAll().block();
	}

	@DisplayName("Test save with requred field not blank")
	@Test
	public void whenSaveWithRequredfieldNotBlank_thenReturnCollectionFindById() {
		Collection collection = createCollection("Mad Max Collection");
		collection = collectionService.save(collection).block();

		Collection found = collectionService.findById(collection.getId()).block();

		assertThat(found.getName()).isEqualTo(collection.getName());
	}

	@DisplayName("Test save with requred field blank")
	@Test
	public void whenSaveWithoutRequredfieldNotBlank_thenThrowsException() {

		Exception exception = assertThrows(ValidationException.class, () -> {
			Collection collection = createCollection("");
			collectionService.save(collection).block();
		});

		assertThat(exception.getMessage()).contains("Collection name is required.");

	}

	@DisplayName("Test update with exist id and field not blank")
	@Test
	public void whenUpdateWithExistIdAndFieldNotBlank_thenUpdateCollection() {
		Collection collection = createCollection("Mad Max Collection");
		collection = collectionService.save(collection).block();

		assertThat(collection.getId()).isNotNull();

		String newName = "Mad Max Collection - Updeted";
		collection.setName(newName);
		Collection collectionUpdate = collectionService.update(collection).block();

		assertThat(collectionUpdate.getId()).isEqualTo(collection.getId());

		assertThat(collectionUpdate.getName()).isEqualTo(newName);

	}

	@DisplayName("Test update with exist id and field blank")
	@Test
	public void whenUpdateWithExistIdAndFieldBlank_thenThrowsException() {

		Exception exception = assertThrows(ValidationException.class, () -> {
			Collection collection = createCollection("Mad Max Collection");
			collection = collectionService.save(collection).block();

			assertThat(collection.getId()).isNotNull();

			collection.setName("");
			collectionService.update(collection).block();
		});

		assertThat(exception.getMessage()).contains("Collection name is required.");

	}

	@DisplayName("Test update with not exist id and field not blank")
	@Test
	public void whenUpdateWithNotExistIdAndFieldNotBlank_thenThrowsException() {

		String idNotExists = "87re6t9876re9t87";
		Exception exception = assertThrows(DatabaseObjectNotFoundException.class, () -> {
			Collection collection = createCollection("Mad Max Collection");
			collection.setId(idNotExists);

			collection.setName("Mad Max Collection - Updated");
			collectionService.update(collection).block();
		});

		assertThat(exception.getMessage()).isEqualTo(
				(String.format("%s with the %s entered in the database was not found.", "Collection", idNotExists)));

	}

	@DisplayName("Test FindAll (for example) Collection")
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
