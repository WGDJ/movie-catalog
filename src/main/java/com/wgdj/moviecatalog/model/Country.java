package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "countries")
public class Country {

	@Id
	private String id;
	
	@NotBlank(message="Country name is required.")
	public String name;

	@NotBlank(message="Country iso31661 is required.")
	public String iso31661;

}
