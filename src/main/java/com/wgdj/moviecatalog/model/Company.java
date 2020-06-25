package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "companies")
public class Company {

	@Id
	private String id;

	@NotBlank(message = "Company name is required.")
	private String name;

	@NotBlank(message = "Company oring country is required.")
	private String originCountry;

}