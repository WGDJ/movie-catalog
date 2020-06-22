package com.wgdj.moviecatalog.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wgdj.moviecatalog.util.mongoCascade.CascadeSave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie implements Entity {

	@Id
	public String id;

	@NotNull(message = "Flag adult is required.")
	public Boolean adult;

	@NotBlank(message = "Homepage is required.")
	public String homepage;

	@NotBlank(message = "Title is required.")
	public String title;

	@NotBlank(message = "Original title is required.")
	public String originalTitle;

	@NotBlank(message = "Overview is required.")
	public String overview;

	@NotBlank(message = "Original language is required.")
	public String originalLanguage;

	@NotNull(message = "Budget is required.")
	public BigDecimal budget;

	@NotBlank(message = "IMB id is required.")
	public String imdbId;

	@NotNull(message = "Release date is required.")
	public Date releaseDate;

	@NotBlank(message = "Status is required.")
	public String status;

	@DBRef
	@CascadeSave
	@NotNull(message = "Collection is required.")
	public Collection belongsToCollection;

	@DBRef
	@CascadeSave
	@NotNull(message = "Genre is required.")
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
