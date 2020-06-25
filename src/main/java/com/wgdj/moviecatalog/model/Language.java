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
@Document(collection = "languages")
public class Language {
	
	@Id
	private String id;

	@NotBlank(message="Language name is required.")
	private String name;
	
	@NotBlank(message="Language iso6391 is required.")
	private String iso6391;

}