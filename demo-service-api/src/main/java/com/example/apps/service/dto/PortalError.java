package com.example.apps.service.dto;

import java.io.Serializable;

import com.example.apps.service.exception.PortalExceptionCode;

public class PortalError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4785003122880348287L;
	
	private String errorCode;
	private String msg;
	
	private Object obj;
	
	public PortalError(String code, String msg) {
		this(code, msg, null);
	}
	
	public PortalError(String code, String msg, Object obj) {
		super();
		this.errorCode = code;
		this.msg = msg;
		this.obj = obj;
	}
	
	public PortalError(PortalExceptionCode exceptionCode) {
		this(exceptionCode.getCode(), 
			exceptionCode.getMsg(), 
			null);
	}
	
	public PortalError(PortalExceptionCode exceptionCode, Object obj) {
		this(exceptionCode.getCode(), 
			exceptionCode.getMsg(), 
			obj);
	}
	
	public PortalError() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String code) {
		this.errorCode = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "PortalError [errorCode=" + errorCode + ", msg=" + msg + "]";
	}
}
