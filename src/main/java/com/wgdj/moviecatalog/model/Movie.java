package com.wgdj.moviecatalog.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "belongsToCollectionId", "genresIds", "productionCompaniesIds",
		"productionCountriesIds", "spokenLanguagesIds" })
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

	@Transient
	public Collection belongsToCollection;

	@NotNull(message = "Collection is required.")
	public String belongsToCollectionId;

	@Transient
	public List<Genre> genres;

	@NotNull(message = "Genre is required.")
	public List<String> genresIds;

	@Transient
	public List<Company> productionCompanies;

	@NotNull(message = "Production companies is required.")
	public List<String> productionCompaniesIds;

	@Transient
	public List<Country> productionCountries;

	@NotNull(message = "Production countries is required.")
	public List<String> productionCountriesIds;

	@Transient
	public List<Language> spokenLanguages;

	@NotNull(message = "Spoken languages is required.")
	public List<String> spokenLanguagesIds;

}
