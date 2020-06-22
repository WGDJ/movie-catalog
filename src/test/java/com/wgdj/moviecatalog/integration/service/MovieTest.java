package com.wgdj.moviecatalog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.service.movie.MovieServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persitence testes for entity Movie")
public class MovieTest {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieServiceInterface movieService;
	
	@Before
	public void init() {
		movieRepository.deleteAll().block();
	}

	@DisplayName("Test persist with requred filds not blank")
	@DirtiesContext
	@Test
	public void whenSaveWithRequredFildsNotBlank_thenReturnMovieFindById() {
		String title = "Mad Max Movie";
		Movie movie = createMovie(title);
		movie = movieService.save(movie).block();

		Movie found = movieService.findById(movie.getId()).block();

		assertThat(found.getTitle()).isEqualTo(movie.getTitle());
		
		assertThat(found.getOriginalTitle()).isEqualTo(String.format("%s - Original ", title));
		
		assertThat(found.getBudget()).isEqualTo(new BigDecimal(57000000.53));
		
//		assertThat(found.getProductionCompanies().size()).isEqualTo(2);
		
	}
//	
//	@DisplayName("Test persist with requred filds blank")
//	@DirtiesContext
//	@Test
//	public void whenPersistWithoutRequredFildsNotBlank_thenThrowsException() {
//		Movie movie = createMovie("");
//		
//		Exception exception = assertThrows(ValidationException.class, () -> {
//			movieService.save(movie).block();
//	    });
//		
//		assertThat(exception.getMessage()).contains("Movie name is required.");
//
//	}
//
//	@DisplayName("Test FindAll (for example) Movie")
//	@DirtiesContext
//	@Test
//	public void whenFindByExample_thenReturn2Movies() {
//		List<Movie> movies = new ArrayList<Movie>();
//		movies.add(createMovie("Mad Max Movie"));
//		movies.add(createMovie("Star Wars Movie"));
//		movies.add(createMovie("Hannibal Lecter"));
//		movies.add(createMovie("Mad Man"));
//		movieRepository.saveAll(movies).blockLast();
//		
//		Movie movieExample = new Movie();
//		movieExample.setName("Mad");
//
//		List<Movie> found = movieService.findAll(movieExample).collectList().block();
//
//		assertThat(found.size()).isEqualTo(2);
//	}
//	
//	@DisplayName("Test ByExample (for example) Movie")
//	@DirtiesContext
//	@Test
//	public void whenFindAll_thenReturn4Movies() {
//		List<Movie> movies = new ArrayList<Movie>();
//		movies.add(createMovie("Mad Max Movie"));
//		movies.add(createMovie("Star Wars Movie"));
//		movies.add(createMovie("Hannibal Lecter"));
//		movies.add(createMovie("Mad Man"));
//		movieRepository.saveAll(movies).blockLast();
//		
//		Movie movieExample = new Movie();
//		movieExample.setName("Mad");
//
//		List<Movie> found = movieService.findAll(movieExample).collectList().block();
//
//		assertThat(found.size()).isEqualTo(2);
//	}
//	
	private Movie createMovie(String title) {
		return Movie.builder().adult(false)
		.homepage(String.format("www.%s.com.", title))
		.title(title)
		.originalTitle(String.format("%s - Original ", title))
		.overview(String.format("Overview of %s ", title))
		.originalLanguage("en")
		.budget(new BigDecimal(57000000.53))
		.imdbId("57377")
		.releaseDate(Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
		.status("Released")
		.belongsToCollection(Collection.builder().name("Hanibal").build())
//		.genres(Arrays.asList(
//				Genre.builder().name("Horror").build(),  
//				Genre.builder().name("Drama").build()))
//		.productionCompanies(Arrays.asList(
//				Company.builder().name("Kennedy Miller Productions").originCountry("US").build(),  
//				Company.builder().name("Warner Bros. Pictures").originCountry("US").build()))
//		.productionCountries(Arrays.asList(
//				Country.builder().name("Australia").iso31661("AU").build(),  
//				Country.builder().name("United States of America").iso31661("US").build()))
//		.spokenLanguages(Arrays.asList(
//				Language.builder().name("Australia").iso6391("au").build(),  
//				Language.builder().name("United States of America").iso6391("us").build()))
		.build();
	}

}
