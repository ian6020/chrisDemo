package com.example.apps.domain.portal;

import java.io.Serializable;

public class UsersPK implements Serializable{

	
	private String usrId;
	private String type;
	
	public UsersPK() {
		// TODO Auto-generated constructor stub
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
