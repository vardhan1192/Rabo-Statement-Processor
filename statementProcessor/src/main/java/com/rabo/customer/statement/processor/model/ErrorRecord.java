package com.rabo.customer.statement.processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRecord {

	private long reference;

	private String accountNumber;

}
