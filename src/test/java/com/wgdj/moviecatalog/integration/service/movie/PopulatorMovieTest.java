package com.wgdj.moviecatalog.integration.service.movie;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.service.company.CompanyService;
import com.wgdj.moviecatalog.service.country.CountryService;
import com.wgdj.moviecatalog.service.genre.GenreService;
import com.wgdj.moviecatalog.service.language.LanguageService;

@Component
public class PopulatorMovieTest {
	
	@Autowired
	private  MovieRepository movieRepository;

	@Autowired
	private GenreService genreService; 
	
	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private LanguageService languageService;
	
	public Movie createAnSaveMovieCascade(String title) {
		Movie movie = Movie.builder()
				.adult(false)
				.homepage(String.format("www.%s.com.".replace(" ", ""), title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.originalLanguage("en")
				.budget(new BigDecimal(57000000.53)).imdbId("57377")
				.releaseDate(
						Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
				.status("Released")
				.belongsToCollection(Collection.builder().name("Hanibal").build())
				.genres(Arrays.asList(Genre.builder().name("Horror").build(), Genre.builder().name("Drama").build()))
				.productionCompanies(
						Arrays.asList(Company.builder().name("Kennedy Miller Productions").originCountry("US").build(),
								Company.builder().name("Warner Bros. Pictures").originCountry("US").build()))
				.productionCountries(Arrays.asList(Country.builder().name("Australia").iso31661("AU").build(),
						Country.builder().name("United States of America").iso31661("US").build()))
				.spokenLanguages(Arrays.asList(Language.builder().name("English").iso6391("au").build(),
						Language.builder().name("English").iso6391("us").build()))
				.build();
		return movieRepository.save(movie).block();
	}

	public Movie createMovie(String title) {
		return Movie.builder()
				.adult(false)
				.homepage(String.format("www.%s.com.".replace(" ", ""), title))
				.title(title)
				.originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title))
				.originalLanguage("en")
				.budget(new BigDecimal(57000000.53)).imdbId("57377")
				.releaseDate(
						Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
				.status("Released")
				.belongsToCollection(Collection.builder().name("Hanibal").build())
				.build();
	}

	public Genre createAndSaveGenre(String name) {
		return genreService.save(Genre.builder().name(name).build()).block();
	}

	public Company createAndSaveCompany(String name) {
		return companyService.save(Company.builder().name(name).originCountry("US").build()).block();
	}

	public Country createAndSaveCountry(String name) {
		return countryService.save(Country.builder().name(name).iso31661("US").build()).block();
	}

	public Language createAndSaveLanguage(String name) {
		return languageService.save(Language.builder().name(name).iso6391("us").build()).block();
	}
	
	public List<Movie> saveAll(List<Movie> movies) {
		return movieRepository.saveAll(movies).collectList().block();
	}
	
	public void clearMongoCollection() {
		movieRepository.deleteAll().block();
	}

}