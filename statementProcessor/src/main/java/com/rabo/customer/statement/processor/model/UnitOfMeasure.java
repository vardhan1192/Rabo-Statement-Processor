package com.rabo.customer.statement.processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitOfMeasure {
	String code;
    String name;
    double price;
    double quantity;
    boolean isOrderable;
}
