package com.wgdj.moviecatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.repository.CollectionRepository;

@Service
public class Test {

	@Autowired
	private CollectionRepository collectionRepository;

	public void whenFindById_thenReturnCollection() {
//		Collection collection = new Collection();
//		collection.setName("Mad Max Collection");
//		collection = collectionRepository.save(collection).block();
//
//		Collection found = collectionRepository.findById(collection.getId()).block();
//		
//		System.out.println(found);
		
		System.out.println(collectionRepository.findAll().blockLast());
	}

}