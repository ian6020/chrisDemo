package com.example.apps.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RewardPoint {
	Integer points;
	
	public RewardPoint() {
		this.points = 0;
	}

	public Integer getPoints() {
		return points;
	}
	
	public void addPoints(Integer points) {
		this.points += points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
}
