package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@Document(collection = "countries")
public class Country implements Entity {

	@Id
	private String id;
	
	@NotBlank(message="Country name is required.")
	public String name;

	@NotBlank(message="Country iso31661 is required.")
	public String iso31661;

}
