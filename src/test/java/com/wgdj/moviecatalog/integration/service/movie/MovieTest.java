package com.wgdj.moviecatalog.integration.service.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.service.movie.MovieServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Persitence testes for entity Movie")
public class MovieTest {

	@Autowired
	private MovieServiceInterface movieService;

	@Autowired
	private PopulatorMovieTest populatorMovieTest;
	
	@Before
	public void init() {
		populatorMovieTest.clearMongoCollections();
	}

//		StepVerifier
	
	@DisplayName("Test save cascade with required filds not null and not blank")
	@Test
	public void whenSaveCascadeWithRequredFildsNotBlank_thenReturnMovieFindById() {
		String title = "Mad Max Movie";
		
		
		Movie movie = populatorMovieTest.createAnSaveMovieCascade(title);

		Movie found = movieService.findById(movie.getId()).block();

		assertThat(found.getTitle()).isEqualTo(movie.getTitle());

		assertThat(found.getOriginalTitle()).isEqualTo(String.format("%s - Original ", title));

		assertThat(found.getBudget()).isEqualTo(new BigDecimal(57000000.53));

		assertThat(found.getGenres().size()).isEqualTo(2);
		assertThat(found.getProductionCompanies().size()).isEqualTo(2);
		assertThat(found.getProductionCountries().size()).isEqualTo(2);
		assertThat(found.getSpokenLanguages().size()).isEqualTo(2);

	}

	@DisplayName("Test save cascade with required fields blank and null")
	@Test
	public void whenSaveCascadeWithoutRequredFildsNotBlank_thenThrowsException() {
		Movie movie = new Movie();
		movie.setTitle(null);

		Exception exception = assertThrows(ValidationException.class, () -> {
			movieService.save(movie).block();
		});

		assertThat(exception.getMessage()).contains("Flag adult is required.");
		assertThat(exception.getMessage()).contains("Production companies is required.");
		assertThat(exception.getMessage()).contains("Homepage is required.");
		assertThat(exception.getMessage()).contains("Title is required.");
		assertThat(exception.getMessage()).contains("Collection is required.");
		assertThat(exception.getMessage()).contains("Spoken languages is required.");
		assertThat(exception.getMessage()).contains("Status is required.");
		assertThat(exception.getMessage()).contains("Production countries is required.");
		assertThat(exception.getMessage()).contains("IMB id is required.");
		assertThat(exception.getMessage()).contains("Release date is required.");
		assertThat(exception.getMessage()).contains("Budget is required.");
		assertThat(exception.getMessage()).contains("Original title is required.");
		assertThat(exception.getMessage()).contains("Genre is required.");
		assertThat(exception.getMessage()).contains("Overview is required.");
		assertThat(exception.getMessage()).contains("Original language is required.");

	}
	
	@DisplayName("Test save with required filds not null and not blank")
	@Test
	public void whenSaveWithRequredFildsNotBlank_thenReturnMovieFindById() {
		
		String title = "Mad Max Movie";
		Movie movie = populatorMovieTest.createMovie(title);
		
		String collectionName = "Hanibal";
		Collection collection = populatorMovieTest.createAndSaveCollection(collectionName);
		String genreName = "Action";
		Genre genre = populatorMovieTest.createAndSaveGenre(genreName);
		String companyName = "Warner Bros. Pictures";
		Company company = populatorMovieTest.createAndSaveCompany(companyName);
		String countryName = "United States of America";
		Country country = populatorMovieTest.createAndSaveCountry(countryName);
		String languageName = "English";
		Language language = populatorMovieTest.createAndSaveLanguage(languageName);
		
		movie.setBelongsToCollectionId(collection.getId());
		movie.setGenresIds(Arrays.asList(genre.getId()));
		movie.setProductionCompaniesIds(Arrays.asList(company.getId()));
		movie.setProductionCountriesIds(Arrays.asList(country.getId()));
		movie.setSpokenLanguagesIds(Arrays.asList(language.getId()));
		
		movie = movieService.save(movie).block();

		Movie found = movieService.findById(movie.getId()).block();

		assertThat(found.getTitle()).isEqualTo(movie.getTitle());

		assertThat(found.getOriginalTitle()).isEqualTo(String.format("%s - Original ", title));

		assertThat(found.getBudget()).isEqualTo(new BigDecimal(57000000.53));

		assertThat(found.getBelongsToCollection()).isNotNull();
		assertThat(found.getBelongsToCollection().getName()).isEqualTo(collectionName);
		
		assertThat(found.getGenres().size()).isEqualTo(1);
		assertThat(found.getGenres().iterator().next().getName()).isEqualTo(genreName);
		
		assertThat(found.getProductionCompanies().size()).isEqualTo(1);
		assertThat(found.getProductionCompanies().iterator().next().getName()).isEqualTo(companyName);
		
		assertThat(found.getProductionCountries().size()).isEqualTo(1);
		assertThat(found.getProductionCountries().iterator().next().getName()).isEqualTo(countryName);
		
		assertThat(found.getSpokenLanguages().size()).isEqualTo(1);
		assertThat(found.getSpokenLanguages().iterator().next().getName()).isEqualTo(languageName);

	}

	@DisplayName("Test update with existing id and field not blank")
	@Test
	public void whenUpdateWithExistIdAndFieldNotBlank_thenUpdateCollection() {
		Movie movie = populatorMovieTest.createAnSaveMovieCascade("Mad Max Movie");

		assertThat(movie.getId()).isNotNull();

		String newTitle = "Mad Max Collection - Updeted";
		movie.setTitle(newTitle);
		movie.setBelongsToCollectionId(movie.getBelongsToCollection().getId());
		movie.setGenresIds(Arrays.asList(movie.getGenres().iterator().next().getId()));
		movie.setProductionCompaniesIds(Arrays.asList(movie.getProductionCompanies().iterator().next().getId()));
		movie.setProductionCountriesIds(Arrays.asList(movie.getProductionCountries().iterator().next().getId()));
		movie.setSpokenLanguagesIds(new ArrayList<String>());

		Movie movieUpdate = movieService.update(movie).block();

		assertThat(movieUpdate.getId()).isEqualTo(movie.getId());
		assertThat(movieUpdate.getTitle()).isEqualTo(newTitle);
		assertThat(movieUpdate.getGenres().size()).isEqualTo(1);
		assertThat(movieUpdate.getProductionCompanies().size()).isEqualTo(1);
		assertThat(movieUpdate.getProductionCountries().size()).isEqualTo(1);
		assertThat(movieUpdate.getSpokenLanguages().size()).isEqualTo(0);

	}

	@DisplayName("Test update with existing id and field blank and null")
	@Test
	public void whenUpdateWithExistIdAndFieldBlankAndNull_thenThrowsException() {

		Exception exception = assertThrows(ValidationException.class, () -> {
			Movie movie = populatorMovieTest.createAnSaveMovieCascade("Mad Max Movie");
			assertThat(movie.getId()).isNotNull();

			String newTitle = "";
			movie.setTitle(newTitle);
			Movie m = movieService.update(movie).block();
			System.out.println(m);
		});

		assertThat(exception.getMessage()).contains("title: Title is required.");

	}

	@DisplayName("Test FindAll Movie")
	@Test
	public void whenFindAll_thenReturn4Movies() {
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Mad Max Movie"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Star Wars Movie"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Hannibal Lecter"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Mad Man"));
		populatorMovieTest.saveAll(movies);

		List<Movie> found1 = movieService.findAll().collectList().block();
		assertThat(found1.size()).isEqualTo(4);

	}

	@DisplayName("Test FindAllByExample Movie")
	@Test
	public void whenFindByExample_thenReturnMovies() {

		List<Movie> movies = new ArrayList<Movie>();
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Mad Max Movie"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Star Wars Movie"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Hannibal Lecter"));
		movies.add(populatorMovieTest.createAnSaveMovieCascade("Mad Man"));
		populatorMovieTest.saveAll(movies);

		String inexistentId = "7bd7e93a-b4d8-11ea-b3de-0242ac130004";

		List<Movie> foundByTitle = movieService.findAll(Movie.builder().title("Mad").build()).collectList().block();
		assertThat(foundByTitle.size()).isEqualTo(2);

		List<Movie> foundByCollection = movieService
				.findAll(Movie.builder()
						.belongsToCollectionId(movies.iterator().next().getBelongsToCollection().getId())
						.build())
				.collectList().block();
		assertThat(foundByCollection.size()).isEqualTo(1);

		List<Movie> notFoundByCollection = movieService
				.findAll(Movie.builder().belongsToCollectionId(inexistentId).build())
				.collectList().block();
		assertThat(notFoundByCollection.size()).isEqualTo(0);

		List<Movie> foundByGenre = movieService
				.findAll(Movie.builder()
						.genresIds(Arrays.asList(movies.iterator().next().getGenres().iterator().next().getId()))
						.build())
				.collectList().block();
		assertThat(foundByGenre.size()).isEqualTo(1);

		List<Movie> notFoundByGenre = movieService
				.findAll(Movie.builder().genresIds(Arrays.asList(inexistentId)).build())
				.collectList().block();
		assertThat(notFoundByGenre.size()).isEqualTo(0);

		List<Movie> foundByCompany = movieService.findAll(Movie.builder()
				.productionCompaniesIds(Arrays.asList(movies.iterator().next().getProductionCompanies().iterator().next().getId()))
				.build()).collectList().block();
		assertThat(foundByCompany.size()).isEqualTo(1);

		List<Movie> notFoundByCompany = movieService.findAll(
				Movie.builder().productionCompaniesIds(Arrays.asList(inexistentId)).build())
				.collectList().block();
		assertThat(notFoundByCompany.size()).isEqualTo(0);

		List<Movie> foundByCountry = movieService.findAll(Movie.builder()
				.productionCountriesIds(Arrays.asList(movies.iterator().next().getProductionCountries().iterator().next().getId()))
				.build()).collectList().block();
		assertThat(foundByCountry.size()).isEqualTo(1);

		List<Movie> notFoundByCountry = movieService.findAll(
				Movie.builder().productionCountriesIds(Arrays.asList(inexistentId)).build())
				.collectList().block();
		assertThat(notFoundByCountry.size()).isEqualTo(0);

		List<Movie> foundByLanguage = movieService.findAll(Movie.builder()
				.spokenLanguagesIds(Arrays.asList(movies.iterator().next().getSpokenLanguages().iterator().next().getId()))
				.build()).collectList().block();
		assertThat(foundByLanguage.size()).isEqualTo(1);

		List<Movie> notFoundByLanguage = movieService.findAll(
				Movie.builder().spokenLanguagesIds(Arrays.asList(inexistentId)).build())
				.collectList().block();
		assertThat(notFoundByLanguage.size()).isEqualTo(0);

	}

	

}
