package com.wgdj.moviecatalog.util.dataBase;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.service.collection.CollectionService;
import com.wgdj.moviecatalog.service.company.CompanyService;
import com.wgdj.moviecatalog.service.country.CountryService;
import com.wgdj.moviecatalog.service.genre.GenreService;
import com.wgdj.moviecatalog.service.language.LanguageService;
import com.wgdj.moviecatalog.service.movie.MovieService;

@Component
public class DataBaseInitialization {

	@Autowired
	private MovieService movieService;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private GenreService genreService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private LanguageService languageService;
	
	public void init() {
		createChilds();
		createMovie("Hannibal");
		createMovie("Silence of the Lambs");
	}

	private Collection collection = null;
	private Genre genge1 = null;
	private Genre genge2 = null;
	private Company company1 = null;
	private Company company2 = null;
	private Country country1 = null;
	private Country country2 = null;
	private Language language1 = null;
	private Language language2 = null;
	
	
	private void createMovie(String title) {
		
		Movie movie = Movie.builder().adult(false).homepage(String.format("www.%s.com.".replace(" ", ""), title))
				.title(title).originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title)).originalLanguage("en")
				.budget(new BigDecimal(57000000.53)).imdbId("57377")
				.releaseDate(
						Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
				.status("Released").belongsToCollectionId(collection.getId())
				.genresIds(Arrays.asList(genge1.getId(), genge2.getId()))
				.productionCompaniesIds(Arrays.asList(company1.getId(), company2.getId()))
				.productionCountriesIds(Arrays.asList(country1.getId(), country2.getId()))
				.spokenLanguagesIds(Arrays.asList(language1.getId(), language2.getId())).build();

		movieService.save(movie).block();
	}
	
	private void createChilds() {
		collection = createAndSaveCollection("Hannibal");
		genge1 = createAndSaveGenre("Horror");
		genge2 = createAndSaveGenre("Drama");
		company1 = createAndSaveCompany("Kennedy Miller Productions");
		company2 = createAndSaveCompany("Warner Bros. Pictures");
		country1 = createAndSaveCountry("Australia");
		country2 = createAndSaveCountry("United States of America");
		language1 = createAndSaveLanguage("English");
		language2 = createAndSaveLanguage("Spanish");
	}
	
	private Collection createAndSaveCollection(String name) {
		return collectionService.save(Collection.builder().name(name).build()).block();
	}

	private Genre createAndSaveGenre(String name) {
		return genreService.save(Genre.builder().name(name).build()).block();
	}

	private Company createAndSaveCompany(String name) {
		return companyService.save(Company.builder().name(name).originCountry("US").build()).block();
	}

	private Country createAndSaveCountry(String name) {
		return countryService.save(Country.builder().name(name).iso31661("US").build()).block();
	}

	private Language createAndSaveLanguage(String name) {
		return languageService.save(Language.builder().name(name).iso6391("us").build()).block();
	}

}