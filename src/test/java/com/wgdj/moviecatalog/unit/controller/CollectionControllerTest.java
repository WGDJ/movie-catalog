package com.wgdj.moviecatalog.unit.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.wgdj.moviecatalog.controller.CollectionController;
import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.dtos.CollectionDTO;
import com.wgdj.moviecatalog.service.collection.CollectionService;
import com.wgdj.moviecatalog.unit.controller.movie.PopulatorControllerMovie;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(CollectionController.class)
@Import({CollectionService.class, ModelMapper.class})
@AutoConfigureWebTestClient(timeout = "5000")
public class CollectionControllerTest {
	
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	private CollectionService collectionService;

	@Test
	public void givenMovies_whenGetAll_thenReturn2()
	  throws Exception {
	     
		Collection collection1 = PopulatorControllerMovie.createAndSaveCollection("Hannibal");
		Collection collection2 = PopulatorControllerMovie.createAndSaveCollection("Star Wars");
		
		when(collectionService.findAll()).thenReturn(Flux.just(collection1, collection2));
		
		webTestClient.get()
	        .uri("/collections")
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBodyList(CollectionDTO.class)
	        .value(movs -> movs.size(), equalTo(2));

	}

}
