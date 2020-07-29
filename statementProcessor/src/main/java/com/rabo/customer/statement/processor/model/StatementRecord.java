package com.rabo.customer.statement.processor.model;

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
	private double startBalance;

	@NotNull
	private String mutation;

	@NotNull
	private double endBalance;

	public StatementRecord(long reference, String accountNumber, String description, double startBalance,
			String mutation, double endBalance) {
		super();
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}

	@Override
	public String toString() {
		return "StatementRecord [reference=" + reference + ", accountNumber=" + accountNumber + ", description="
				+ description + ", startBalance=" + startBalance + ", mutation=" + mutation + ", endBalance="
				+ endBalance + "]";
	}
}