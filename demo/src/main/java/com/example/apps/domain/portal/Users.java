package com.example.apps.domain.portal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "DB2.USERS")
@IdClass(value=UsersPK.class)
public class Users {

	
	@Id
	@Column(name="usr_id")
	private String usrId;
	 
	@Id
	@Column(name="type")
	private String type;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="address")
	private String address;
	 
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
	
	public Users() {
		// TODO Auto-generated constructor stub
	}

}
