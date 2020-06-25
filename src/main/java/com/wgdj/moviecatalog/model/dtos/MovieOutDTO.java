package com.wgdj.moviecatalog.model.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MovieOutDTO {

	private String id;

	private Boolean adult;

	private String homepage;

	private String title;

	private String originalTitle;

	private String overview;

	private String originalLanguage;

	private BigDecimal budget;

	private String imdbId;

	private Date releaseDate;

	private String status;

	private CollectionDTO belongsToCollection;

	private List<GenreDTO> genres;

	private List<CompanyDTO> productionCompanies;

	private List<CountryDTO> productionCountries;

	private List<LanguageDTO> spokenLanguages;

}
