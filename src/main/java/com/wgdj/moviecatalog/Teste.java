package com.wgdj.moviecatalog;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgdj.moviecatalog.model.Collection;
import com.wgdj.moviecatalog.model.Company;
import com.wgdj.moviecatalog.model.Country;
import com.wgdj.moviecatalog.model.Genre;
import com.wgdj.moviecatalog.model.Language;
import com.wgdj.moviecatalog.model.Movie;
import com.wgdj.moviecatalog.service.collection.CollectionService;
import com.wgdj.moviecatalog.service.collection.CollectionServiceInterface;
import com.wgdj.moviecatalog.service.movie.MovieServiceInterface;

@Service
public class Teste {

	@Autowired
	private MovieServiceInterface movieService;
	
	public void testar() {
		
//		Collection c = collectionService.save(Collection.builder().name("Hannibal").build()).block();
		
		String title = "Mad Max Movie";
		Movie movie = createMovie(title);
		movie = movieService.save(movie).block();
		
		Movie found = movieService.findById(movie.getId()).block();
		
		System.out.println(found);
		
	}
	
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
		.belongsToCollection(Collection.builder().name("Hannibal").build())
		.genres(Arrays.asList(
				Genre.builder().name("Horror").build(),  
				Genre.builder().name("Drama").build()))
		.productionCompanies(Arrays.asList(
				Company.builder().name("Kennedy Miller Productions").originCountry("US").build(),  
				Company.builder().name("Warner Bros. Pictures").originCountry("US").build()))
		.productionCountries(Arrays.asList(
				Country.builder().name("Australia").iso31661("AU").build(),  
				Country.builder().name("United States of America").iso31661("US").build()))
		.spokenLanguages(Arrays.asList(
				Language.builder().name("Australia").iso6391("au").build(),  
				Language.builder().name("United States of America").iso6391("us").build()))
		.build();
	}

}