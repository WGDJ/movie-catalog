package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "languages")
public class Language {
	
	@Id
	@Indexed(unique = true)
	public String id;

	@NotBlank(message="Language name is required.")
	public String name;
	
	@NotBlank(message="Language iso6391 is required.")
	public String iso6391;

}