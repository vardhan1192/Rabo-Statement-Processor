package com.rabo.customer.statement.processor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class ErrorRecord {

	@Id
	@GeneratedValue
	private long reference;

	private String accountNumber;

}
