package com.rabo.customer.statementProcessor.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.model.StatementRecord;

public interface ReferenceAndEndBalValidationService {

	public ResponseEntity<ResponseDetails> getErrorDetails(List<StatementRecord> statementsRecords);
}
