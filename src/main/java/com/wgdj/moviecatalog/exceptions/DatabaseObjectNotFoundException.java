package com.wgdj.moviecatalog.exceptions;
public class DatabaseObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseObjectNotFoundException(final String object, String id) {
		super(String.format("%s with the %s entered in the database was not found.", object, id));
	}

}