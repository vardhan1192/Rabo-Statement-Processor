package com.rabo.customer.statement.processor.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
	
	int itemNumber;
    String name;
    boolean isFrozen;
    int departmentNumber;
    String departmentName;
    List<UnitOfMeasure> uoms;
    boolean isCrafted;


}
