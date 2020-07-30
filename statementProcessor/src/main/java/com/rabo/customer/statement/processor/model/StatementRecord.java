package com.rabo.customer.statement.processor.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel
@AllArgsConstructor
public class StatementRecord {

	@Max(value = 999999)
	@Min(value = 100000)
	@ApiModelProperty("Reference is b/w 100000 and 999999")
	private long reference;

	@NotNull
	private String accountNumber;

	private String description;

	@JsonProperty("start_Balance")
	@ApiModelProperty("Leading zeors are not allowed. Leads to Bad Request")
	private double startBalance;

	@NotNull
	private String mutation;

	@JsonProperty("end_Balance")
	@ApiModelProperty("Leading zeors are not allowed. Leads to Bad Request")
	private double endBalance;
}