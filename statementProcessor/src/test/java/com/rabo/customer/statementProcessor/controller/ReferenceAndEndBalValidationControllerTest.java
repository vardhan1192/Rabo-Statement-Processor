package com.rabo.customer.statementProcessor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rabo.customer.statementProcessor.model.ErrorRecord;
import com.rabo.customer.statementProcessor.model.ResponseDetails;
import com.rabo.customer.statementProcessor.model.StatementRecord;
import com.rabo.customer.statementProcessor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statementProcessor.util.Constants;

@ExtendWith(SpringExtension.class)
public class ReferenceAndEndBalValidationControllerTest {
	
	@Mock
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;
	
	@InjectMocks
	private ReferenceAndEndBalValidationController referenceAndEndBalValidationController;
		
	@Test
	public void testGetTransactionsSuccessful() {
		
		ResponseEntity<ResponseDetails> expected = ResponseEntity.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));
		
		List<ErrorRecord> errorRecord = new ArrayList<>();
		List<StatementRecord> successfulStatementsRecords  = new ArrayList<>();
		
		successfulStatementsRecords.add(new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -20.23));
		successfulStatementsRecords.add(new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		successfulStatementsRecords.add(new StatementRecord(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, "44.5", 131.16));
		successfulStatementsRecords.add(new StatementRecord(183356, "NL74ABNA0248990274", "Subscription for Peter de Vries", 92.98, "-46.65", 46.33));
		
		
		when(referenceAndEndBalValidationService.filterDuplicateRefrences(successfulStatementsRecords)).thenReturn(errorRecord);
		when(referenceAndEndBalValidationService.filterEndBalMismatchRecords(successfulStatementsRecords)).thenReturn(errorRecord);
		
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationController.getTransactions(successfulStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(0, actual.getBody().getErrorRecords().size());
	}
	
	@Test
	public void testGetTransactionsDuplicateRefrerences() { 
		
		List<ErrorRecord> duplicateReferenceErrorRecord = new ArrayList<>();
		List<ErrorRecord> endBalaceIncorrectErrorRecord = new ArrayList<>();
		
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL93ABNA0585619023"));
		
		ResponseEntity<ResponseDetails> expected = ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE, duplicateReferenceErrorRecord));
		
		
		List<StatementRecord> duplicateRefrerencesStatementsRecords  = new ArrayList<>();
		
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -20.23));
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, "-10.91", 79.92));
		duplicateRefrerencesStatementsRecords.add(new StatementRecord(112806, "NL93ABNA0585619023", "Tickets from Richard Bakker", 102.12, "+45.87", 147.99));
		
		when(referenceAndEndBalValidationService.filterDuplicateRefrences(duplicateRefrerencesStatementsRecords)).thenReturn(duplicateReferenceErrorRecord);
		when(referenceAndEndBalValidationService.filterEndBalMismatchRecords(duplicateRefrerencesStatementsRecords)).thenReturn(endBalaceIncorrectErrorRecord);
		
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationController.getTransactions(duplicateRefrerencesStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(2, actual.getBody().getErrorRecords().size());
	}
	
	@Test
	public void testGetTransactionsIncorrectEndBalance() { 
		
		List<ErrorRecord> duplicateReferenceErrorRecord = new ArrayList<>();
		List<ErrorRecord> endBalaceIncorrectErrorRecord = new ArrayList<>();
		
		endBalaceIncorrectErrorRecord.add(new ErrorRecord(194261, "NL91RABO0315273637"));
		
		ResponseEntity<ResponseDetails> expected = ResponseEntity.ok(new ResponseDetails(Constants.INCORRECT_END_BALANCE, duplicateReferenceErrorRecord));
		
		List<StatementRecord> incorrectEndBalanceStatementRecords  = new ArrayList<>();
		
		incorrectEndBalanceStatementRecords.add(new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -30.23));
		incorrectEndBalanceStatementRecords.add(new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		incorrectEndBalanceStatementRecords.add(new StatementRecord(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, "-10.91", 79.92));
		incorrectEndBalanceStatementRecords.add(new StatementRecord(112806, "NL93ABNA0585619023", "Tickets from Richard Bakker", 102.12, "+45.87", 147.99));
		
		when(referenceAndEndBalValidationService.filterDuplicateRefrences(incorrectEndBalanceStatementRecords)).thenReturn(duplicateReferenceErrorRecord);
		when(referenceAndEndBalValidationService.filterEndBalMismatchRecords(incorrectEndBalanceStatementRecords)).thenReturn(endBalaceIncorrectErrorRecord);
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationController.getTransactions(incorrectEndBalanceStatementRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(1, actual.getBody().getErrorRecords().size());
	}
	
	@Test
	public void testGetTransactionsDuplicaterefrenceAndIncorrectEndBalance() { 
		
		List<ErrorRecord> duplicateReferenceErrorRecord = new ArrayList<>();
		List<ErrorRecord> endBalaceIncorrectErrorRecord = new ArrayList<>();
		
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL93ABNA0585619023"));
		
		endBalaceIncorrectErrorRecord.add(new ErrorRecord(194261, "NL91RABO0315273637"));
		
		ResponseEntity<ResponseDetails> expected = ResponseEntity.ok(new ResponseDetails(Constants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, duplicateReferenceErrorRecord));
		
		
		List<StatementRecord> duplicaterefrenceAndIncorrectEndBalanceStatementsRecords  = new ArrayList<>();
		
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -30.23));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, "-10.91", 79.92));
		duplicaterefrenceAndIncorrectEndBalanceStatementsRecords.add(new StatementRecord(112806, "NL93ABNA0585619023", "Tickets from Richard Bakker", 102.12, "+45.87", 147.99));
		
		when(referenceAndEndBalValidationService.filterDuplicateRefrences(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords)).thenReturn(duplicateReferenceErrorRecord);
		when(referenceAndEndBalValidationService.filterEndBalMismatchRecords(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords)).thenReturn(endBalaceIncorrectErrorRecord);
		
		
		
		ResponseEntity<ResponseDetails> actual = referenceAndEndBalValidationController.getTransactions(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords);
		
		assertEquals(expected.getBody().getResult(), actual.getBody().getResult());
		assertEquals(3, actual.getBody().getErrorRecords().size());
	}	
}
