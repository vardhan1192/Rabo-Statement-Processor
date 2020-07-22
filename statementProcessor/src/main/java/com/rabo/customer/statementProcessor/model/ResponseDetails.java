package com.rabo.customer.statementProcessor.model;

import java.util.List;

public class ResponseDetails {
	
	private String result;
	
	private List<ErrorRecord> errorRecords;

	public ResponseDetails(String result, List<ErrorRecord> errorRecords) {
		super();
		this.result = result;
		this.errorRecords = errorRecords;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

	public void setErrorRecords(List<ErrorRecord> errorRecords) {
		this.errorRecords = errorRecords;
	}
}
