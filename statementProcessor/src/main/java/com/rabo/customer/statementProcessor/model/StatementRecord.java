package com.rabo.customer.statementProcessor.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StatementRecord {
	
	@Max(value=999999)
	@Min(value=100000)
	@ApiModelProperty("Reference is b/w 100000 and 999999")
	private long reference;
	
	@NotNull
	private String accountNumber;
	
	private String description;
	
	@NotNull
	private double start_Balance;
	
	@NotNull
	private String mutation;
	
	@NotNull
	private double end_Balance;

	public StatementRecord(long reference, String accountNumber, String description, double start_Balance, String mutation,
			double end_Balance) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.start_Balance = start_Balance;
		this.mutation = mutation;
		this.end_Balance = end_Balance;
	}

	public long getReference() {
		return reference;
	}

	public void setReference(long transRef) {
		this.reference = transRef;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accNo) {
		this.accountNumber = accNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStart_Balance() {
		return start_Balance;
	}

	public void setStart_Balance(double start_Balance) {
		this.start_Balance = start_Balance;
	}

	public String getMutation() {
		return mutation;
	}

	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	public double getEnd_Balance() {
		return end_Balance;
	}

	public void setEnd_Balance(double end_Balance) {
		this.end_Balance = end_Balance;
	}

	@Override
	public String toString() {
		return "StatementRecord [reference=" + reference + ", accountNumber=" + accountNumber + ", description=" + description
				+ ", start_Balance=" + start_Balance + ", mutation=" + mutation + ", end_Balance=" + end_Balance + "]";
	}
}