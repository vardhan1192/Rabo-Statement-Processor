package com.rabo.customer.statement.processor.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDetails {

	private String result;

	private List<ErrorRecord> errorRecords;

}
