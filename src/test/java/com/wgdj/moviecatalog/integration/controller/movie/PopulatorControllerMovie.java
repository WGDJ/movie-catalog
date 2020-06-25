package com.wgdj.moviecatalog.integration.controller.movie;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;

@Component
public class PopulatorControllerMovie {


	public static Movie createMovieWithAllChilds(String title) {

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

		return movie;
	}

	private static Collection createAndSaveCollection(String name) {
		return Collection.builder().name(name).build();
	}

	private static Genre createAndSaveGenre(String name) {
		return Genre.builder().name(name).build();
	}

	private static Company createAndSaveCompany(String name) {
		return Company.builder().name(name).originCountry("US").build();
	}

	private static Country createAndSaveCountry(String name) {
		return Country.builder().name(name).iso31661("US").build();
	}

	private static Language createAndSaveLanguage(String name) {
		return Language.builder().name(name).iso6391("us").build();
	}

}
