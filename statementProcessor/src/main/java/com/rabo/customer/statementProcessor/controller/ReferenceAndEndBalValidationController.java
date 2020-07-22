package com.rabo.customer.statementProcessor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.statementProcessor.exceptions.BadRequest;
import com.rabo.customer.statementProcessor.exceptions.InternalServerError;
import com.rabo.customer.statementProcessor.model.ErrorRecord;
import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.model.StatementRecord;
import com.rabo.customer.statementProcessor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statementProcessor.util.Constants;

@RestController
public class ReferenceAndEndBalValidationController {
	
	@Autowired
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;

	@GetMapping("/transactions")
	public ResponseEntity<ResponseDetails> getTransactions(@Valid @RequestBody List<StatementRecord> statementsRecords)
	 throws BadRequest{
		
		List<ErrorRecord> dupilcateReferences = referenceAndEndBalValidationService.filterDuplicateRefrences(statementsRecords);
		List<ErrorRecord> endBalMismatchRecords = referenceAndEndBalValidationService.filterEndBalMismatchRecords(statementsRecords);
		
		List<ErrorRecord> allErrorRecords = new ArrayList<ErrorRecord>();
		allErrorRecords.addAll(dupilcateReferences);
		allErrorRecords.addAll(endBalMismatchRecords);
		
		if(dupilcateReferences.size() == 0 && endBalMismatchRecords.size() == 0) {
			return ResponseEntity.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));
		}else if(dupilcateReferences.size() != 0 && endBalMismatchRecords.size() == 0) {
			return ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE, dupilcateReferences));
		}else if(dupilcateReferences.size() == 0 && endBalMismatchRecords.size() != 0){
			return ResponseEntity.ok(new ResponseDetails(Constants.INCORRECT_END_BALANCE, endBalMismatchRecords));
		}else if(dupilcateReferences.size() != 0 && endBalMismatchRecords.size() != 0){
			return ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, allErrorRecords));
		}else {
			 throw new InternalServerError(Constants.INTERNAL_SERVER_ERROR);
		}
	}
}