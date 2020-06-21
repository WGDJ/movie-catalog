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
@Document(collection = "countries")
public class Country {

	@Id
	@Indexed(unique = true)
	private String id;
	
	@NotBlank(message="Country name is required.")
	public String name;

	@NotBlank(message="Country iso31661 is required.")
	public String iso31661;

}
