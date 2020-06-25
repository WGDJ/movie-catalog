package com.wgdj.moviecatalog.unit.controller.movie;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.UUID;

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

		Movie movie = Movie.builder().id(UUID.randomUUID().toString()).adult(false).homepage(String.format("www.%s.com.".replace(" ", ""), title))
				.title(title).originalTitle(String.format("%s - Original ", title))
				.overview(String.format("Overview of %s ", title)).originalLanguage("en")
				.budget(new BigDecimal(57000000.53)).imdbId("57377")
				.releaseDate(
						Date.from(LocalDate.of(2001, 02, 20).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
				.status("Released").belongsToCollectionId(collection.getId())
				.genres(Arrays.asList(genge1, genge2))
				.productionCompanies(Arrays.asList(company1, company2))
				.productionCountries(Arrays.asList(country1, country2))
				.spokenLanguages(Arrays.asList(language1, language2)).build();

		return movie;
	}

	public static Collection createAndSaveCollection(String name) {
		return Collection.builder().id(UUID.randomUUID().toString()).name(name).build();
	}

	private static Genre createAndSaveGenre(String name) {
		return Genre.builder().id(UUID.randomUUID().toString()).name(name).build();
	}

	private static Company createAndSaveCompany(String name) {
		return Company.builder().id(UUID.randomUUID().toString()).name(name).originCountry("US").build();
	}

	private static Country createAndSaveCountry(String name) {
		return Country.builder().id(UUID.randomUUID().toString()).name(name).iso31661("US").build();
	}

	private static Language createAndSaveLanguage(String name) {
		return Language.builder().id(UUID.randomUUID().toString()).name(name).iso6391("us").build();
	}

}
