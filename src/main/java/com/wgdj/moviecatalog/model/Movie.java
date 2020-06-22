package com.wgdj.moviecatalog.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wgdj.moviecatalog.util.mongoCascade.CascadeSave;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@Document(collection = "movies")
public class Movie implements Entity {

	@Id
	public String id;

	@NotNull(message = "Flag adult is required.")
	public Boolean adult;

	@NotNull(message = "Homepage is required.")
	public String homepage;

	@NotNull(message = "Title is required.")
	public String title;

	@NotNull(message = "Original title is required.")
	public String originalTitle;

	@NotNull(message = "Overview is required.")
	public String overview;

	@NotNull(message = "Original language is required.")
	public String originalLanguage;

	@NotNull(message = "Budget is required.")
	public BigDecimal budget;

	@NotNull(message = "IMB id is required.")
	public String imdbId;

	@NotNull(message = "Release date is required.")
	public Date releaseDate;

	@NotNull(message = "Status is required.")
	public String status;

	@DBRef
	@CascadeSave
	@NotNull(message = "Movie collection is required.")
	public Collection belongsToCollection;

	@DBRef
	@CascadeSave
	@NotNull(message = "Movie genre is required.")
	public List<Genre> genres;
	
	@DBRef
	@CascadeSave
	@NotNull(message = "Production companies is required.")
	public List<Company> productionCompanies;

	@DBRef
	@CascadeSave
	@NotNull(message = "Production countries is required.")
	public List<Country> productionCountries;

	@DBRef
	@CascadeSave
	@NotNull(message = "Spoken languages is required.")
	public List<Language> spokenLanguages;

}
