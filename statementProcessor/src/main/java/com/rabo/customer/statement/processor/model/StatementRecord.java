package com.rabo.customer.statementProcessor.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class StatementRecord {

	@Max(value = 999999)
	@Min(value = 100000)
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

	public StatementRecord(long reference, String accountNumber, String description, double start_Balance,
			String mutation, double end_Balance) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.start_Balance = start_Balance;
		this.mutation = mutation;
		this.end_Balance = end_Balance;
	}

	@Override
	public String toString() {
		return "StatementRecord [reference=" + reference + ", accountNumber=" + accountNumber + ", description="
				+ description + ", start_Balance=" + start_Balance + ", mutation=" + mutation + ", end_Balance="
				+ end_Balance + "]";
	}
}