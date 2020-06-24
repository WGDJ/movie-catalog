package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "languages")
public class Language {
	
	@Id
	public String id;

	@NotBlank(message="Language name is required.")
	public String name;
	
	@NotBlank(message="Language iso6391 is required.")
	public String iso6391;

}