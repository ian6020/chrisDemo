package com.example.apps.service.exception;

public enum PortalExceptionCode {
	E01("E01", "Invalid Parameter"),
	E02("E02", "Invalid Internal Operation"),
	E03("E03", "No Data"),
	E04("E04", "Session expired"),
	E05("E05", "Unauthorized Access");
	
	private String code;
	private String msg;
	
	private PortalExceptionCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
