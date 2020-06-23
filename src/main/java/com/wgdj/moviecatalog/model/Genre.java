package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "genres")
public class Genre implements Entity {

	@Id
	public String id;

	@NotBlank(message="Genre name is required.")
	public String name;

}