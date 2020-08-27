package com.rabo.customer.statement.processor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customer.statement.processor.model.Item;
import com.rabo.customer.statement.processor.model.UnitOfMeasure;

@RestController
public class Question {
	
	@PostMapping("/inventory")
    public static List<Item> findInventory(@RequestBody List<Item> items){
    	List<Item> finalList =  new ArrayList<>();
    	List<Item> initialFilter = items.stream()
    			.filter(p-> ((p.getDepartmentNumber()==40||p.getDepartmentNumber()==41)&&p.isFrozen()))
    			.collect(Collectors.toList());

    	
    	for(Item item:initialFilter) {
    		List<UnitOfMeasure> uoms = item.getUoms();
    	
    		int count = 0;
    		
    			for(UnitOfMeasure uom:uoms) {
    				if(uom.getCode().equals("CS")) {
    					count++;
    				}
    				if(uom.getCode().equals("PLT")) {
    					count++;
    				}
    			}
    		if(count==2) {
    			finalList.add(item);
    		}
    	}
    	return finalList;
    }
}