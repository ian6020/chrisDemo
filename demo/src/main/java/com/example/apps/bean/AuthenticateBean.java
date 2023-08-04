package com.example.apps.bean;

import com.example.apps.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonInclude(Include.NON_NULL)
public class AuthenticateBean {
	
	@Pattern(regexp = AppConstant.STRING_VALUE_FORMAT, message="Invalid UserID.")
	@NotNull(message = "UserID cannot be null")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
