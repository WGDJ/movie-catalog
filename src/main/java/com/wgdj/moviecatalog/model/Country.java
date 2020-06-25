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
@Document(collection = "countries")
public class Country {

	@Id
	private String id;
	
	@NotBlank(message="Country name is required.")
	private String name;

	@NotBlank(message="Country iso31661 is required.")
	private String iso31661;

}
