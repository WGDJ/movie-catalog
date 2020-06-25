package com.wgdj.moviecatalog.unit.controller.movie;

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

import com.wgdj.moviecatalog.controller.MovieController;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.model.dtos.MovieOutDTO;
import com.wgdj.moviecatalog.service.movie.MovieService;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(MovieController.class)
@Import({MovieService.class, ModelMapper.class})
@AutoConfigureWebTestClient(timeout = "5000")
public class MovieControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	private MovieService movieService;

	@Test
	public void givenMovies_whenGetAll_thenReturn2()
	  throws Exception {
	     
		Movie movie1 = PopulatorControllerMovie.createMovieWithAllChilds("Hannibal");
		Movie movie2 = PopulatorControllerMovie.createMovieWithAllChilds("Mad Max Collection");
		
		when(movieService.findAll()).thenReturn(Flux.just(movie1, movie2));
		
		webTestClient.get()
	        .uri("/movies")
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBodyList(MovieOutDTO.class)
	        .value(movs -> movs.size(), equalTo(2));

	}

}
