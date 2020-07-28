package com.rabo.customer.statementProcessor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.statementProcessor.exceptions.BadRequest;
import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.model.StatementRecord;
import com.rabo.customer.statementProcessor.service.ReferenceAndEndBalValidationService;

@RestController
public class ReferenceAndEndBalValidationController {

	@Autowired
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;

	@GetMapping("/transactions")
	public ResponseEntity<ResponseDetails> getTransactions(@Valid @RequestBody List<StatementRecord> statementsRecords)
			throws BadRequest {

		return referenceAndEndBalValidationService.getErrorDetails(statementsRecords);

	}
}