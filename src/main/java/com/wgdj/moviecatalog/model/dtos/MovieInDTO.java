package com.wgdj.moviecatalog.model.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class MovieInDTO {

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

	private String belongsToCollectionId;

	private List<String> genresIds;

	private List<String> productionCompaniesIds;

	private List<String> productionCountriesIds;

	private List<String> spokenLanguagesIds;

}
