package com.example.apps.bean;

import java.math.BigDecimal;

public class ValidateValue {

	protected String validateStr(String value) {
		if(value == null || value.trim().equalsIgnoreCase("null") || value.trim().isEmpty())
			return "";
		else return value;
	}
	
	protected BigDecimal validateInt(String value) {
		if(value == null || value.trim().equalsIgnoreCase("null") || value.trim().isEmpty()) 
			return new BigDecimal(0);
		else return new BigDecimal(value);
	}
	
	protected Boolean validateBool(String value) {
		if(value != null && value.trim().equalsIgnoreCase("Y") && !value.trim().isEmpty()) 
			return true;
		else return false;
	}
}
