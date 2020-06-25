package com.wgdj.moviecatalog.integration.controller.movie;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.wgdj.moviecatalog.controller.MovieController;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.service.movie.MovieService;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(MovieController.class)
@Import(MovieService.class)
public class MovieControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	private MovieService movieService;

	@Test
	public void givenCollections_whenGetAllCollections_thenReturnJsonArray()
	  throws Exception {
	     
		Movie movie1 = PopulatorControllerMovieTest.createMovieWithAllChilds("Hannibal");
		Movie movie2 = PopulatorControllerMovieTest.createMovieWithAllChilds("Mad Max Collection");
		
		Flux<Movie> movies =  Flux.just(movie1, movie2);
		
		when(movieService.findAll()).thenReturn(movies);
		
		webTestClient.get()
	        .uri("/movies")
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBodyList(Movie.class)
	        .value(movs -> movs.size(), equalTo(2));

	}

}
