package com.rabo.customer.statement.processor.model;

import lombok.Data;

@Data
public class ErrorRecord {

	private long reference;

	private String accountNumber;

	public ErrorRecord(long reference, String accountNumber) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "ErrorRecord [reference=" + reference + ", accountNumber=" + accountNumber + "]";
	}
}
