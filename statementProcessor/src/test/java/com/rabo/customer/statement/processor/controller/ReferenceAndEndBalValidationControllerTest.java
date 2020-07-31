package com.rabo.customer.statement.processor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customer.statement.processor.controller.ReferenceAndEndBalValidationController;
import com.rabo.customer.statement.processor.model.ErrorRecord;
import com.rabo.customer.statement.processor.model.ResponseDetails;
import com.rabo.customer.statement.processor.model.StatementRecord;
import com.rabo.customer.statement.processor.service.ReferenceAndEndBalValidationService;
import com.rabo.customer.statement.processor.util.Constants;

@WebMvcTest(value = ReferenceAndEndBalValidationController.class)
class ReferenceAndEndBalValidationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReferenceAndEndBalValidationService referenceAndEndBalValidationService;

	@Test
	void testGetTransactionsSuccessful() throws Exception {

		ResponseEntity<ResponseDetails> responseEntity = ResponseEntity
				.ok(new ResponseDetails(Constants.SUCCESSFUL, new ArrayList<>()));

		List<StatementRecord> successfulStatementsRecords = new ArrayList<>();

		successfulStatementsRecords.add(
				new StatementRecord(194261, "NL91RABO0315273637", "Clothes from Jan Bakker", 21.6, "-41.83", -20.23));
		successfulStatementsRecords.add(
				new StatementRecord(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, "+15.57", 106.8));
		successfulStatementsRecords
				.add(new StatementRecord(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, "44.5", 131.16));
		successfulStatementsRecords.add(new StatementRecord(183356, "NL74ABNA0248990274",
				"Subscription for Peter de Vries", 92.98, "-46.65", 46.33));

		when(referenceAndEndBalValidationService.getErrorDetails(successfulStatementsRecords))
				.thenReturn(responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(successfulStatementsRecords)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"result\":\"SUCCESSFUL\",\"errorRecords\":[]}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	void testGetTransactionsDuplicateRefrerences() throws Exception {

		List<ErrorRecord> duplicateReferenceErrorRecord = new ArrayList<>();

		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		duplicateReferenceErrorRecord.add(new ErrorRecord(112806, "NL93ABNA0585619023"));

		ResponseEntity<ResponseDetails> responseEntity = ResponseEntity
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

		when(referenceAndEndBalValidationService.getErrorDetails(duplicateRefrerencesStatementsRecords))
				.thenReturn(responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(duplicateRefrerencesStatementsRecords)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"result\":\"DUPLICATE_REFERENCE\",\"errorRecords\":[{\"reference\":112806,\"accountNumber\":\"NL69ABNA0433647324\"},{\"reference\":112806,\"accountNumber\":\"NL93ABNA0585619023\"}]}";
		assertEquals(expected, result.getResponse().getContentAsString());

	}

	@Test
	void testGetTransactionsIncorrectEndBalance() throws Exception {

		List<ErrorRecord> endBalaceIncorrectErrorRecord = new ArrayList<>();

		endBalaceIncorrectErrorRecord.add(new ErrorRecord(194261, "NL91RABO0315273637"));

		ResponseEntity<ResponseDetails> responseEntity = ResponseEntity
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

		when(referenceAndEndBalValidationService.getErrorDetails(incorrectEndBalanceStatementRecords))
				.thenReturn(responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(incorrectEndBalanceStatementRecords)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"result\":\"INCORRECT_END_BALANCE\",\"errorRecords\":[{\"reference\":194261,\"accountNumber\":\"NL91RABO0315273637\"}]}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	void testGetTransactionsDuplicaterefrenceAndIncorrectEndBalance() throws Exception {

		List<ErrorRecord> allErrorRecords = new ArrayList<>();

		allErrorRecords.add(new ErrorRecord(112806, "NL69ABNA0433647324"));
		allErrorRecords.add(new ErrorRecord(112806, "NL93ABNA0585619023"));
		allErrorRecords.add(new ErrorRecord(194261, "NL91RABO0315273637"));

		ResponseEntity<ResponseDetails> responseEntity = ResponseEntity
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

		when(referenceAndEndBalValidationService
				.getErrorDetails(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords)).thenReturn(responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(mapToJson(duplicaterefrenceAndIncorrectEndBalanceStatementsRecords))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"result\":\"DUPLICATE_REFERENCE_INCORRECT_END_BALANCE\",\"errorRecords\":[{\"reference\":112806,\"accountNumber\":\"NL69ABNA0433647324\"},{\"reference\":112806,\"accountNumber\":\"NL93ABNA0585619023\"},{\"reference\":194261,\"accountNumber\":\"NL91RABO0315273637\"}]}";
		assertEquals(expected, result.getResponse().getContentAsString());

	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
}
