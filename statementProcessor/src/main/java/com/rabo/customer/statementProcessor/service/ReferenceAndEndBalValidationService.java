package com.rabo.customer.statementProcessor.service;

import java.util.List;

import com.rabo.customer.statementProcessor.model.ErrorRecord;
import com.rabo.customer.statementProcessor.model.StatementRecord;

public interface ReferenceAndEndBalValidationService {

	public List<ErrorRecord> filterDuplicateRefrences(List<StatementRecord> statementsRecords);
	
	public List<ErrorRecord> filterEndBalMismatchRecords(List<StatementRecord> statementsRecords);
}
