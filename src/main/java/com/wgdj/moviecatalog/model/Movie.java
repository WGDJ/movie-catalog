package com.wgdj.moviecatalog.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "movies")
public class Movie {

	@Id
	@Indexed(unique = true)
	public String id;
	
	@NotNull(message="Flag adult is required.")
	public Boolean adult;
	
	@DBRef
	@NotNull(message="Movie collection is required.")
	public Collection belongsToCollection;
	
	public BigDecimal budget;

	@DBRef
	@NotNull(message="Movie genre is required.")
	public List<Genre> genres = null;

	public String homepage;
	public String imdbId;
	public String originalLanguage;
	public String originalTitle;
	public String overview;
	public BigDecimal popularity;

	@DBRef
	@NotNull(message="Movie production company is required.")
	public List<Company> productionCompanies = null;

	@DBRef
	public List<Country> productionCountries = null;

	public String releaseDate;
	public Long revenue;
	public Long runtime;

	@DBRef
	public List<Language> spokenLanguages = null;

	public String status;
	public String tagline;
	public String title;
	public boolean video;
	public float voteAverage;
	public long voteCount;

}
