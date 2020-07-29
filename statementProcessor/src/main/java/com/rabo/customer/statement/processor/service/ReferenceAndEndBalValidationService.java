package com.rabo.customer.statement.processor.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rabo.customer.statement.processor.model.ResponseDetails;
import com.rabo.customer.statement.processor.model.StatementRecord;

public interface ReferenceAndEndBalValidationService {

	public ResponseEntity<ResponseDetails> getErrorDetails(List<StatementRecord> statementsRecords);
}
