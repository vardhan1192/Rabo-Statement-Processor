package com.rabo.customer.statement.processor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.statement.processor.model.ResponseDetails;
import com.rabo.customer.statement.processor.model.StatementRecord;
import com.rabo.customer.statement.processor.service.ReferenceAndEndBalValidationService;

@RestController
public class ReferenceAndEndBalValidationController {

	@Autowired
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;

	@PostMapping("/transactions")
	public ResponseEntity<ResponseDetails> getTransactions(@Valid @RequestBody List<StatementRecord> statementsRecords) {

		return referenceAndEndBalValidationService.getErrorDetails(statementsRecords);

	}
}