package com.example.apps.service.exception;

public class PortalBaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710346576162442239L;

	private String code;
	private String msg;
	private Object obj;
	
	public PortalBaseException(String code, String msg, Object obj) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}
	
	public PortalBaseException(String code, String msg) {
		this(code, msg, null);
	}
	
	public PortalBaseException(PortalExceptionCode portalCode) {
		this(portalCode.getCode(), portalCode.getMsg());
	}
	
	public PortalBaseException(PortalExceptionCode portalCode, Object obj) {
		this(portalCode.getCode(), portalCode.getMsg(), obj);
	}
	
	public PortalBaseException(PortalExceptionCode portalCode, 
		String customMessage) {
		this(customMessage, portalCode.getCode());
	}
	
	public PortalBaseException(PortalExceptionCode portalCode, 
		String customMessage, Object obj) {
		this(customMessage, portalCode.getCode());
	}
	
	public PortalExceptionCode getPortalExceptionCode() {
		return PortalExceptionCode.valueOf(code);
	}

	public Object getObj() {
		return obj;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
