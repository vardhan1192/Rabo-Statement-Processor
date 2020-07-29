package com.rabo.customer.statement.processor.model;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDetails {

	private String result;

	private List<ErrorRecord> errorRecords;

	public ResponseDetails(String result, List<ErrorRecord> errorRecords) {
		super();
		this.result = result;
		this.errorRecords = errorRecords;
	}

}
