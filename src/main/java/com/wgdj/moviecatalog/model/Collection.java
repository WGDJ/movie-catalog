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
@Document(collection = "collections")
public class Collection implements Entity {

	@Id
	public String id;

	@NotBlank(message = "Collection name is required.")
	public String name;

}