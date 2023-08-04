package com.example.apps.bean;

import java.math.BigDecimal;

public class Transaction extends ValidateValue  {
	String transId;
	BigDecimal totalPurchase;
	String transDate;
	Boolean isClaim;
	
	public Transaction(Object[] obj) {
		this.transId = validateStr(String.valueOf(obj[0]));
		this.totalPurchase = validateInt(String.valueOf(obj[1]));
		this.transDate = validateStr(String.valueOf(obj[2]));
		this.isClaim = validateBool(String.valueOf(obj[3]));
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public BigDecimal getTotalPurchase() {
		return totalPurchase;
	}
	
	public BigDecimal getTotalPurchaseABS() {
		return totalPurchase.abs();
	}

	public void setTotalPurchase(BigDecimal totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public Boolean getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Boolean isClaim) {
		this.isClaim = isClaim;
	}
	
	public int getPoint(int maxSpent, int earn) {
		if(this.totalPurchase.intValue() > maxSpent) {
			int diff = Math.abs(this.totalPurchase.intValue()) - maxSpent;
			if(earn > 0) 
				return diff * earn;
		}
		return 0;
	}

}
