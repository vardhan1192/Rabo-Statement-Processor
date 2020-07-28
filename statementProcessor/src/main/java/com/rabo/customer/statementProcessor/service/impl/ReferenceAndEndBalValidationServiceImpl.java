package com.rabo.customer.statementProcessor.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabo.customer.statementProcessor.exceptions.InternalServerError;
import com.rabo.customer.statementProcessor.model.ErrorRecord;
import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.model.StatementRecord;
import com.rabo.customer.statementProcessor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statementProcessor.util.Constants;

@Service
public class ReferenceAndEndBalValidationServiceImpl implements ReferenceAndEndBalValidationService {

	@Override
	public ResponseEntity<ResponseDetails> getErrorDetails(List<StatementRecord> statementsRecords) {
		List<ErrorRecord> dupilcateReferences = this.filterDuplicateRefrences(statementsRecords);
		List<ErrorRecord> endBalMismatchRecords = this.filterEndBalMismatchRecords(statementsRecords);

		List<ErrorRecord> allErrorRecords = new ArrayList<ErrorRecord>();
		allErrorRecords.addAll(dupilcateReferences);
		allErrorRecords.addAll(endBalMismatchRecords);

		if (dupilcateReferences.size() == 0 && endBalMismatchRecords.size() == 0) {
			return ResponseEntity.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));
		} else if (dupilcateReferences.size() != 0 && endBalMismatchRecords.size() == 0) {
			return ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE, dupilcateReferences));
		} else if (dupilcateReferences.size() == 0 && endBalMismatchRecords.size() != 0) {
			return ResponseEntity.ok(new ResponseDetails(Constants.INCORRECT_END_BALANCE, endBalMismatchRecords));
		} else if (dupilcateReferences.size() != 0 && endBalMismatchRecords.size() != 0) {
			return ResponseEntity
					.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, allErrorRecords));
		} else {
			throw new InternalServerError(Constants.INTERNAL_SERVER_ERROR);
		}
	}

	private List<ErrorRecord> filterDuplicateRefrences(List<StatementRecord> statementRecords) {

		List<ErrorRecord> dupicateErrorRecords = new ArrayList<>();
		Set<Long> nonDuplicateReferences = new HashSet<>();

		dupicateErrorRecords = statementRecords.stream().filter(e -> !nonDuplicateReferences.add(e.getReference()))
				.map(e -> new ErrorRecord(e.getReference(), e.getAccountNumber())).collect(Collectors.toList());
		return dupicateErrorRecords;
	}

	private List<ErrorRecord> filterEndBalMismatchRecords(List<StatementRecord> statementsRecords) {

		DecimalFormat df = new DecimalFormat("0.00");

		List<ErrorRecord> endBalMismatchRecords = new ArrayList<>();

		endBalMismatchRecords = statementsRecords.stream()
				.filter(e -> Double.valueOf(df.format(e.getStart_Balance() + Double.valueOf(e.getMutation()))) != e
						.getEnd_Balance())
				.map(e -> new ErrorRecord(e.getReference(), e.getAccountNumber())).collect(Collectors.toList());
		return endBalMismatchRecords;
	}

}
