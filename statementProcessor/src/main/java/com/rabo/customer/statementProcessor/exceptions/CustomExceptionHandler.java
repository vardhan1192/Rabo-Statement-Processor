package com.rabo.customer.statementProcessor.exceptions;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.util.Constants;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(InternalServerError.class)
	public final ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request){
		ResponseDetails responseDetails= new ResponseDetails(Constants.INTERNAL_SERVER_ERROR, new ArrayList<>());
		
		return new ResponseEntity<>(responseDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequest.class)
	public final ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request){
		ResponseDetails responseDetails= new ResponseDetails(Constants.BAD_REQUEST, new ArrayList<>());
		
		return new ResponseEntity<>(responseDetails,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
			ResponseDetails responseDetails= new ResponseDetails(Constants.BAD_REQUEST, new ArrayList<>());
			
			return new ResponseEntity<>(responseDetails,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ResponseDetails responseDetails= new ResponseDetails(Constants.INTERNAL_SERVER_ERROR, new ArrayList<>());
		
		return new ResponseEntity<>(responseDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}