package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Document(collection = "collections")
public class Collection {

	@Id
	@Indexed(unique = true)
	public String id;

	@NotBlank(message = "Collection name is required.")
	public String name;

}