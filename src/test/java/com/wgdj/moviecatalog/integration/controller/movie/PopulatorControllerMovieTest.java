package com.wgdj.moviecatalog.integration.controller.movie;

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
import com.wgdj.moviecatalog.repository.CollectionRepository;
import com.wgdj.moviecatalog.repository.CompanyRepository;
import com.wgdj.moviecatalog.repository.CountryRepository;
import com.wgdj.moviecatalog.repository.GenreRepository;
import com.wgdj.moviecatalog.repository.LanguageRepository;
import com.wgdj.moviecatalog.repository.MovieRepository;
import com.wgdj.moviecatalog.service.collection.CollectionService;
import com.wgdj.moviecatalog.service.company.CompanyService;
import com.wgdj.moviecatalog.service.country.CountryService;
import com.wgdj.moviecatalog.service.genre.GenreService;
import com.wgdj.moviecatalog.service.language.LanguageService;
import com.wgdj.moviecatalog.service.movie.MovieService;

@Component
public class PopulatorControllerMovieTest {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private LanguageRepository languageRepository;

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

	public Movie createAnSaveMovieWithAllChilds(String title) {

		Collection collection = createAndSaveCollection("Hannibal");
		Genre genge1 = createAndSaveGenre("Horror");
		Genre genge2 = createAndSaveGenre("Drama");
		Company company1 = createAndSaveCompany("Kennedy Miller Productions");
		Company company2 = createAndSaveCompany("Warner Bros. Pictures");
		Country country1 = createAndSaveCountry("Australia");
		Country country2 = createAndSaveCountry("United States of America");
		Language language1 = createAndSaveLanguage("English");
		Language language2 = createAndSaveLanguage("Spanish");

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

		return movieService.save(movie).block();
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

	private void saveAll(List<Movie> movies) {
		movieRepository.saveAll(movies);
	}

	public void clearMongoCollections() {
		collectionRepository.deleteAll();
		genreRepository.deleteAll().block();
		companyRepository.deleteAll().block();
		countryRepository.deleteAll().block();
		languageRepository.deleteAll().block();
		movieRepository.deleteAll().block();
	}

}
