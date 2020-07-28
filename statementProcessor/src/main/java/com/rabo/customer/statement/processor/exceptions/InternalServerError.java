package com.rabo.customer.statementProcessor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalServerError(String arg0) {
		super(arg0);
	}
}
