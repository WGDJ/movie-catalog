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
public class Movie {

	@Id
	private String id;

	@NotNull(message = "Flag adult is required.")
	private Boolean adult;

	@NotBlank(message = "Homepage is required.")
	private String homepage;

	@NotBlank(message = "Title is required.")
	private String title;

	@NotBlank(message = "Original title is required.")
	private String originalTitle;

	@NotBlank(message = "Overview is required.")
	private String overview;

	@NotBlank(message = "Original language is required.")
	private String originalLanguage;

	@NotNull(message = "Budget is required.")
	private BigDecimal budget;

	@NotBlank(message = "IMB id is required.")
	private String imdbId;

	@NotNull(message = "Release date is required.")
	private Date releaseDate;

	@NotBlank(message = "Status is required.")
	private String status;

	@Transient
	private Collection belongsToCollection;

	@NotNull(message = "Collection is required.")
	private String belongsToCollectionId;

	@Transient
	private List<Genre> genres;

	@NotNull(message = "Genre is required.")
	private List<String> genresIds;

	@Transient
	private List<Company> productionCompanies;

	@NotNull(message = "Production companies is required.")
	private List<String> productionCompaniesIds;

	@Transient
	private List<Country> productionCountries;

	@NotNull(message = "Production countries is required.")
	private List<String> productionCountriesIds;

	@Transient
	private List<Language> spokenLanguages;

	@NotNull(message = "Spoken languages is required.")
	private List<String> spokenLanguagesIds;

}
