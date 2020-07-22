package com.rabo.customer.statementProcessor.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rabo.customer.statementProcessor.model.ErrorRecord;
import com.rabo.customer.statementProcessor.model.StatementRecord;
import com.rabo.customer.statementProcessor.service.ReferenceAndEndBalValidationService;

@Service
public class ReferenceAndEndBalValidationServiceImpl implements ReferenceAndEndBalValidationService  {

	@Override
	public List<ErrorRecord> filterDuplicateRefrences(List<StatementRecord> statementRecords) {
		
		List<ErrorRecord> dupicateErrorRecords = new ArrayList<>();
		Set<Long> nonDuplicateReferences = new HashSet<>();
		
		for(StatementRecord statementRecord: statementRecords) {
			
			
			if(!nonDuplicateReferences.add(statementRecord.getReference())) {
				ErrorRecord errorRecord = new ErrorRecord(statementRecord.getReference(), statementRecord.getAccountNumber());
				dupicateErrorRecords.add(errorRecord);
			}
			
		}
		return dupicateErrorRecords;
	}

	@Override
	public List<ErrorRecord> filterEndBalMismatchRecords(List<StatementRecord> statementsRecords) {
		
		DecimalFormat df = new DecimalFormat("0.00");

		List<ErrorRecord> endBalMismatchRecords = new ArrayList<>();
		for(StatementRecord statementRecord:statementsRecords) {
			
			double mutation = Double.valueOf(statementRecord.getMutation());
			double finalEndBal = Double.valueOf(df.format(statementRecord.getStart_Balance()+mutation));
			
			if(finalEndBal != statementRecord.getEnd_Balance()){
				ErrorRecord errorRecord = new ErrorRecord(statementRecord.getReference(), statementRecord.getAccountNumber());
				endBalMismatchRecords.add(errorRecord);
			}
		}
		return endBalMismatchRecords;
	}

}
