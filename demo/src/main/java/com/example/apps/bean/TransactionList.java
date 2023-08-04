 package com.example.apps.bean;

import java.util.ArrayList;
import java.util.List;

public class TransactionList {
	public List<Transaction> myList;
	
	public TransactionList(List<Object> obj) {
		this.myList = new ArrayList<Transaction>();
		obj.forEach(data -> this.myList.add(new Transaction((Object[]) data)));
	}
	
	public Integer getPoints(int maxSpent, int earn) {
		Integer total = 0;
		if(maxSpent > 0) {
			total = myList.stream()
					.filter(x -> (x.getIsClaim() == false) && (x.getTotalPurchaseABS().intValue() > maxSpent))
					.mapToInt(c -> c.getPoint(maxSpent, earn))
					.sum();
		}
		return total;
	}
	
}
 