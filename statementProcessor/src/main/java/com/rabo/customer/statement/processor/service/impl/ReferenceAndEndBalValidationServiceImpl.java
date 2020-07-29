package com.rabo.customer.statement.processor.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabo.customer.statement.processor.exceptions.InternalServerError;
import com.rabo.customer.statement.processor.model.ErrorRecord;
import com.rabo.customer.statement.processor.model.ResponseDetails;
import com.rabo.customer.statement.processor.model.StatementRecord;
import com.rabo.customer.statement.processor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statement.processor.util.Constants;

@Service
public class ReferenceAndEndBalValidationServiceImpl implements ReferenceAndEndBalValidationService {

	@Override
	public ResponseEntity<ResponseDetails> getErrorDetails(List<StatementRecord> statementsRecords) {
		List<ErrorRecord> dupilcateReferences = this.filterDuplicateRefrences(statementsRecords);
		List<ErrorRecord> endBalMismatchRecords = this.filterEndBalMismatchRecords(statementsRecords);

		List<ErrorRecord> allErrorRecords = new ArrayList<>();
		allErrorRecords.addAll(dupilcateReferences);
		allErrorRecords.addAll(endBalMismatchRecords);

		if (dupilcateReferences.isEmpty() && endBalMismatchRecords.isEmpty()) {
			return ResponseEntity.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));
		} else if (!dupilcateReferences.isEmpty() && endBalMismatchRecords.isEmpty()) {
			return ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE, dupilcateReferences));
		} else if (dupilcateReferences.isEmpty() && !endBalMismatchRecords.isEmpty()) {
			return ResponseEntity.ok(new ResponseDetails(Constants.INCORRECT_END_BALANCE, endBalMismatchRecords));
		} else if (!dupilcateReferences.isEmpty() && !endBalMismatchRecords.isEmpty()) {
			return ResponseEntity
					.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, allErrorRecords));
		} else {
			throw new InternalServerError(Constants.INTERNAL_SERVER_ERROR);
		}
	}

	private List<ErrorRecord> filterDuplicateRefrences(List<StatementRecord> statementRecords) {

		Set<Long> nonDuplicateReferences = new HashSet<>();

		return statementRecords.stream().filter(e -> !nonDuplicateReferences.add(e.getReference()))
				.map(e -> new ErrorRecord(e.getReference(), e.getAccountNumber())).collect(Collectors.toList());

	}

	private List<ErrorRecord> filterEndBalMismatchRecords(List<StatementRecord> statementsRecords) {

		DecimalFormat df = new DecimalFormat("0.00");

		return statementsRecords.stream()
				.filter(e -> Double.valueOf(df.format(e.getStartBalance() + Double.valueOf(e.getMutation()))) != e
						.getEndBalance())
				.map(e -> new ErrorRecord(e.getReference(), e.getAccountNumber())).collect(Collectors.toList());

	}

}
