package com.wgdj.moviecatalog.util.request;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wgdj.moviecatalog.exception.DatabaseObjectNotFoundException;
import com.wgdj.moviecatalog.exception.EmptyResponseException;

@RestControllerAdvice
public class RestControllerHandler {

	@ExceptionHandler({ DatabaseObjectNotFoundException.class })
	public ResponseEntity<Mensagem> handleNotFound(final RuntimeException ex) {
		return new ResponseEntity<Mensagem>(Mensagem.builder().message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ EmptyResponseException.class })
	public ResponseEntity<Mensagem> handleEmptyResponse(final RuntimeException ex) {
		return new ResponseEntity<Mensagem>(Mensagem.builder().message(ex.getMessage()).build(), HttpStatus.OK);
	}

}