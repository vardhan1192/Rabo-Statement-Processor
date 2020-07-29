package com.rabo.customer.statement.processor.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;

import com.rabo.customer.statement.processor.model.ErrorRecord;
import com.rabo.customer.statement.processor.model.ResponseDetails;
import com.rabo.customer.statement.processor.model.StatementRecord;
import com.rabo.customer.statement.processor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statement.processor.service.impl.ReferenceAndEndBalValidationServiceImpl;
import com.rabo.customer.statement.processor.util.Constants;

@WebMvcTest(value = ReferenceAndEndBalValidationServiceImpl.class)
class ReferenceAndEndBalValidationServiceImplTest {
	
	@Autowired
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;
	

	@Test
	void testGetErrorDetailsForSuccessfulMessage() {
		
		ResponseEntity<ResponseDetails> expected = ResponseEntity.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));
		
		List<StatementRecord> successfulStatementsRecords = new ArrayList<>();

		successfulStatementsRecords.add(
				new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -20.23));
		successfulStatementsRecords.add(
				new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		successfulStatementsRecords
				.add(new StatementRecord(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, "44.5", 131.16));
		successfulStatementsRecords.add(new StatementRecord(183356, "NL74ABNA0248990274",
				"Subscription for Peter de Vries", 92.98, "-46.65", 46.33));
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationService.getErrorDetails(successfulStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(0, actual.getBody().getErrorRecords().size());
	}
	
	@Test
	void testGetErrorDetailsForDuplicateReferenceMessage() {
		
		List<ErrorRecord> duplicateReferenceErrorRecord = new ArrayList<>();

		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL93ABNA0585619023"));

		ResponseEntity<ResponseDetails> expected = ResponseEntity
				.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE, duplicateReferenceErrorRecord));

		List<StatementRecord> duplicateRefrerencesStatementsRecords = new ArrayList<>();

		duplicateRefrerencesStatementsRecords.add(
				new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -20.23));
		duplicateRefrerencesStatementsRecords.add(
				new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(112806, "NL69ABNA0433647324",
				"Clothes for Richard de Vries", 90.83, "-10.91", 79.92));
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(112806, "NL93ABNA0585619023",
				"Tickets from Richard Bakker", 102.12, "+45.87", 147.99));

		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationService.getErrorDetails(duplicateRefrerencesStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(2, actual.getBody().getErrorRecords().size());
	
	}
	
	@Test
	void testGetErrorDetailsForIncorrectBalanceMessage() {
		
		List<ErrorRecord> endBalaceIncorrectErrorRecord = new ArrayList<>();

		endBalaceIncorrectErrorRecord.add(new ErrorRecord(194261, "NL91RABO0315273637"));

		ResponseEntity<ResponseDetails> expected = ResponseEntity
				.ok(new ResponseDetails(Constants.INCORRECT_END_BALANCE, endBalaceIncorrectErrorRecord));

		List<StatementRecord> incorrectEndBalanceStatementRecords = new ArrayList<>();

		incorrectEndBalanceStatementRecords.add(
				new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -30.23));
		incorrectEndBalanceStatementRecords.add(
				new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		incorrectEndBalanceStatementRecords
				.add(new StatementRecord(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, "44.5", 131.16));
		incorrectEndBalanceStatementRecords.add(new StatementRecord(183356, "NL74ABNA0248990274",
				"Subscription for Peter de Vries", 92.98, "-46.65", 46.33));

		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationService.getErrorDetails(incorrectEndBalanceStatementRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(1, actual.getBody().getErrorRecords().size());
	}
	
	@Test
	void testGetErrorDetailsForDuplicateReferenceAndIncorrectBalanceMessage() {
		List<ErrorRecord> allErrorRecords = new ArrayList<>();

		allErrorRecords.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		allErrorRecords.add(new ErrorRecord(112806, "NL93ABNA0585619023"));
		allErrorRecords.add(new ErrorRecord(194261, "NL91RABO0315273637"));

		ResponseEntity<ResponseDetails> expected = ResponseEntity
				.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, allErrorRecords));

		List<StatementRecord> duplicaterefrenceAndIncorrectEndBalanceStatementsRecords = new ArrayList<>();

		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(
				new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -30.23));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(
				new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(112806, "NL69ABNA0433647324",
				"Clothes for Richard de Vries", 90.83, "-10.91", 79.92));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(112806, "NL93ABNA0585619023",
				"Tickets from Richard Bakker", 102.12, "+45.87", 147.99));
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationService.getErrorDetails(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(3, actual.getBody().getErrorRecords().size());
	}

}
