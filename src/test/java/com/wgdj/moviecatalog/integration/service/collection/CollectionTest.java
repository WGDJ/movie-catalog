package com.wgdj.moviecatalog.integration.service.collection;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionTest {

	@Autowired
	private CollectionRepository collectionRepository;

	@Test
	public void whenFindById_thenReturnCollection() {
		Collection collection = new Collection();
		collection.setName("Mad Max Collection");
		collection = collectionRepository.save(collection).block();

		Collection found = collectionRepository.findById(collection.getId()).block();
		
		assertThat(found.getName()).isEqualTo(collection.getName());
	}

}
