package com.rabo.customer.statementProcessor.model;

public class ErrorRecord {

	private long reference;
	
	private String accountNumber;

	public ErrorRecord(long reference, String accountNumber) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
	}

	public long getReference() {
		return reference;
	}

	public void setReference(long reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "ErrorRecord [reference=" + reference + ", accountNumber=" + accountNumber + "]";
	}
}
