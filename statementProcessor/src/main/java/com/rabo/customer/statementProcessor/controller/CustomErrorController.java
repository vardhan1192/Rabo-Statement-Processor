package com.rabo.customer.statementProcessor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.statementProcessor.exceptions.BadRequest;
import com.rabo.customer.statementProcessor.exceptions.InternalServerError;
import com.rabo.customer.statementProcessor.util.Constants;

@RestController
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public void handleError(HttpServletRequest request) {
	      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	      if(statusCode==400) {
	    	  throw new BadRequest(Constants.BAD_REQUEST);  
	      }else {
	    	  request.setAttribute("javax.servlet.error.status_code", 500);
	    	  throw new InternalServerError(Constants.INTERNAL_SERVER_ERROR);
	      }
	      
	  }
	
	@Override
	public String getErrorPath() {
		return "/error";
	}

}
