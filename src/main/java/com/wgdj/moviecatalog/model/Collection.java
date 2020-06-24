package com.wgdj.moviecatalog.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "collections")
public class Collection {

	@Id
	public String id;

	@NotBlank(message = "Collection name is required.")
	public String name;

}