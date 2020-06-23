package com.wgdj.moviecatalog.exception;
public class EmptyResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyResponseException(final String message) {
		super(String.format("Não foi encontrada Cidade com os parâmetros informados [%s].", message));
	}

}