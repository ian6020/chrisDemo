package com.example.apps.bean;

import java.io.Serializable;

import com.example.apps.service.exception.PortalBaseException;
import com.example.apps.service.exception.PortalExceptionCode;

public class PortalErrorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4785003122880348287L;
	
	private String errorCode;
	private String msg;
	private transient Object obj;
	
	public PortalErrorBean(String code, String msg) {
		this(code, msg, null);
	}
	
	public PortalErrorBean(String code, String msg, Object obj) {
		super();
		this.errorCode = code;
		this.msg = msg;
		this.obj = obj;
	}
	
	public PortalErrorBean(PortalBaseException e) {
		this(e.getCode(), e.getMessage(), e.getObj());
	}
	
	public PortalErrorBean(PortalExceptionCode exceptionCode) {
		this(exceptionCode.getCode(), 
			exceptionCode.getMsg(), 
			null);
	}
	
	public PortalErrorBean(PortalExceptionCode exceptionCode, String msg, Object obj) {
		this(exceptionCode.getCode(), msg, obj);
	}
	
	public PortalErrorBean() {
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
		return "PortalErrorBean [errorCode=" + errorCode + ", msg=" + msg + "]";
	}
}
