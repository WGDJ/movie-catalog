package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@Document(collection = "companies")
public class Company {

	@Id
	@Indexed(unique = true)
	public String id;

	@NotBlank(message = "Company name is required.")
	public String name;

	@NotBlank(message = "Company oring country is required.")
	public String originCountry;

}