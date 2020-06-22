package com.wgdj.moviecatalog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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

	@DisplayName("Test save with required filds not null and not blank")
	@Test
	public void whenSaveWithRequredFildsNotBlank_thenReturnMovieFindById() {
		String title = "Mad Max Movie";
		Movie movie = createMovie(title);
		movie = movieService.save(movie).block();

		Movie found = movieService.findById(movie.getId()).block();

		assertThat(found.getTitle()).isEqualTo(movie.getTitle());

		assertThat(found.getOriginalTitle()).isEqualTo(String.format("%s - Original ", title));

		assertThat(found.getBudget()).isEqualTo(new BigDecimal(57000000.53));

		assertThat(found.getGenres().size()).isEqualTo(2);
		assertThat(found.getProductionCompanies().size()).isEqualTo(2);
		assertThat(found.getProductionCountries().size()).isEqualTo(2);
		assertThat(found.getSpokenLanguages().size()).isEqualTo(2);

	}

	@DisplayName("Test save with required fields blank and null")
	@Test
	public void whenPersistWithoutRequredFildsNotBlank_thenThrowsException() {
		Movie movie = new Movie();
		movie.setTitle(null);

		Exception exception = assertThrows(ValidationException.class, () -> {
			movieService.save(movie).block();
		});

		assertThat(exception.getMessage()).contains("adult: Flag adult is required.");
		assertThat(exception.getMessage()).contains("productionCompanies: Production companies is required.");
		assertThat(exception.getMessage()).contains("homepage: Homepage is required.");
		assertThat(exception.getMessage()).contains("title: Title is required.");
		assertThat(exception.getMessage()).contains("belongsToCollection: Collection is required.");
		assertThat(exception.getMessage()).contains("spokenLanguages: Spoken languages is required.");
		assertThat(exception.getMessage()).contains("status: Status is required.");
		assertThat(exception.getMessage()).contains("productionCountries: Production countries is required.");
		assertThat(exception.getMessage()).contains("imdbId: IMB id is required.");
		assertThat(exception.getMessage()).contains("releaseDate: Release date is required.");
		assertThat(exception.getMessage()).contains("budget: Budget is required.");
		assertThat(exception.getMessage()).contains("originalTitle: Original title is required.");
		assertThat(exception.getMessage()).contains("genres: Genre is required.");
		assertThat(exception.getMessage()).contains("overview: Overview is required.");
		assertThat(exception.getMessage()).contains("originalLanguage: Original language is required.");

	}

	@DisplayName("Test update with existing id and field not blank")
	@Test
	public void whenUpdateWithExistIdAndFieldNotBlank_thenUpdateCollection() {
		Movie movie = createMovie("Mad Max Movie");
		movie = movieService.save(movie).block();

		assertThat(movie.getId()).isNotNull();

		String newTitle = "Mad Max Collection - Updeted";
		movie.setTitle(newTitle);
		movie.setGenres(Arrays.asList(movie.getGenres().iterator().next()));
		movie.setProductionCompanies(Arrays.asList(movie.getProductionCompanies().iterator().next()));
		movie.setProductionCountries(Arrays.asList(movie.getProductionCountries().iterator().next()));
		movie.setSpokenLanguages(new ArrayList<Language>());

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
			Movie movie = createMovie("Mad Max Movie");
			movie = movieService.save(movie).block();
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
		movies.add(createMovie("Mad Max Movie"));
		movies.add(createMovie("Star Wars Movie"));
		movies.add(createMovie("Hannibal Lecter"));
		movies.add(createMovie("Mad Man"));
		movieRepository.saveAll(movies).blockLast();

		List<Movie> found1 = movieService.findAll().collectList().block();
		assertThat(found1.size()).isEqualTo(4);

	}

	@DisplayName("Test FindAllByExample Movie")
	@Test
	public void whenFindByExample_thenReturnMovies() {
		
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(createMovie("Mad Max Movie"));
		movies.add(createMovie("Star Wars Movie"));
		movies.add(createMovie("Hannibal Lecter"));
		movies.add(createMovie("Mad Man"));
		movieRepository.saveAll(movies).blockLast();
		
		String inexistentId = "7bd7e93a-b4d8-11ea-b3de-0242ac130004";

		List<Movie> foundByTitle = movieService.findAll(Movie.builder().title("Mad").build()).collectList().block();
		assertThat(foundByTitle.size()).isEqualTo(2);

		List<Movie> foundByCollection = movieService
				.findAll(Movie.builder()
						.belongsToCollection(Collection.builder()
								.id(movies.iterator().next().getBelongsToCollection().getId()).build())
						.build())
				.collectList().block();
		assertThat(foundByCollection.size()).isEqualTo(1);
		
		List<Movie> notFoundByCollection = movieService
				.findAll(Movie.builder()
						.belongsToCollection(Collection.builder()
								.id(inexistentId).build())
						.build())
				.collectList().block();
		assertThat(notFoundByCollection.size()).isEqualTo(0);

		List<Movie> foundByGenre = movieService
				.findAll(Movie.builder()
						.genres(Arrays.asList(Genre.builder()
								.id(movies.iterator().next().getGenres().iterator().next().getId()).build()))
						.build())
				.collectList().block();
		assertThat(foundByGenre.size()).isEqualTo(1);
		
		
		
		List<Movie> notFoundByGenre = movieService
				.findAll(Movie.builder().genres(Arrays.asList(Genre.builder().id(inexistentId).build())).build())
				.collectList().block();
		assertThat(notFoundByGenre.size()).isEqualTo(0);
		
		List<Movie> foundByCompany = movieService
				.findAll(Movie.builder()
						.productionCompanies(Arrays.asList(Company.builder()
								.id(movies.iterator().next().getProductionCompanies().iterator().next().getId()).build()))
						.build())
				.collectList().block();
		assertThat(foundByCompany.size()).isEqualTo(1);
		
		List<Movie> notFoundByCompany = movieService
				.findAll(Movie.builder().productionCompanies(Arrays.asList(Company.builder().id(inexistentId).build())).build())
				.collectList().block();
		assertThat(notFoundByCompany.size()).isEqualTo(0);
		
		List<Movie> foundByCountry = movieService
				.findAll(Movie.builder()
						.productionCountries(Arrays.asList(Country.builder()
								.id(movies.iterator().next().getProductionCountries().iterator().next().getId()).build()))
						.build())
				.collectList().block();
		assertThat(foundByCountry.size()).isEqualTo(1);
		
		List<Movie> notFoundByCountry = movieService
				.findAll(Movie.builder().productionCountries(Arrays.asList(Country.builder().id(inexistentId).build())).build())
				.collectList().block();
		assertThat(notFoundByCountry.size()).isEqualTo(0);
		
		List<Movie> foundByLanguage = movieService
				.findAll(Movie.builder()
						.spokenLanguages(Arrays.asList(Language.builder()
								.id(movies.iterator().next().getSpokenLanguages().iterator().next().getId()).build()))
						.build())
				.collectList().block();
		assertThat(foundByLanguage.size()).isEqualTo(1);
		
		List<Movie> notFoundByLanguage = movieService
				.findAll(Movie.builder().spokenLanguages(Arrays.asList(Language.builder().id(inexistentId).build())).build())
				.collectList().block();
		assertThat(notFoundByLanguage.size()).isEqualTo(0);

	}

	private Movie createMovie(String title) {
		return Movie.builder().adult(false).homepage(String.format("www.%s.com.".replace(" ", ""), title)).title(title)
				.originalTitle(String.format("%s - Original ", title)).overview(String.format("Overview of %s ", title))
				.originalLanguage("en").budget(new BigDecimal(57000000.53)).imdbId("57377")
				.releaseDate(
						Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
				.status("Released").belongsToCollection(Collection.builder().name("Hanibal").build())
				.genres(Arrays.asList(Genre.builder().name("Horror").build(), Genre.builder().name("Drama").build()))
				.productionCompanies(
						Arrays.asList(Company.builder().name("Kennedy Miller Productions").originCountry("US").build(),
								Company.builder().name("Warner Bros. Pictures").originCountry("US").build()))
				.productionCountries(Arrays.asList(Country.builder().name("Australia").iso31661("AU").build(),
						Country.builder().name("United States of America").iso31661("US").build()))
				.spokenLanguages(Arrays.asList(Language.builder().name("Australia").iso6391("au").build(),
						Language.builder().name("United States of America").iso6391("us").build()))
				.build();
	}

}
