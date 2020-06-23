package com.wgdj.moviecatalog.exception;
public class DatabaseObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseObjectNotFoundException(final String object, final String id) {
		super(String.format("No %s found with the given id: %s.", object, id));
	}

}