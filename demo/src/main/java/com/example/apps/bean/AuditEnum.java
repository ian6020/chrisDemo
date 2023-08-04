package com.example.apps.bean;

public enum AuditEnum {
	NONE ("", ""),
	Successful ("SUCC", "Successful"),
	InvalidSQLOperation ("E02", "Invalid SQL Operation"),
	InvalidParameter ("E03", "Invalid Parameter"),
	InvalidInternalOperation ("E04", "Invalid Internal Operation"),
	NoData ("E04", "No Data"),
	FileNotExists ("E05", "File Not Exists"),
	UnauthorizedAccess ("E06", "Unauthorized Access"),
	InvalidHeaderParameter ("E07", "Invalid Header Parameter"),
	InvalidURL ("E08", "Invalid URL"),
	InvalidQuotation ("E09", "Invalid Quotation"),
	ContactCompulsory ("E10", "Either mobile/tel/work phone is compulsory"),
	MobileDuplicated ("E11", "Mobile Duplicated"),
	InvalidMobileNumber ("E12", "Invalid Mobile Number"),
	InvalidTelNumber ("E13", "Invalid Tel Number"),
	InsuffientDataAccess ("E14", "You are not authorised to check policy details. Please contact the Customer Care Department."),
	CriteriaResultNotFound ("E15", "There is no record found from the criteria searched, please check your data."),
	SFTP_FAILED("E16", "Transfer to Intermediate server failed."),
	ENCRYPTION_FAILED("E17", "Encryption Failed");
	
	private String code;
	private String message;
	
	public static final String ANONYMOUS = "ANONYMOUS";
	public static final String OPERATING_SYSTEM = "OS";
	public static final String BROWSER = "browser";
	public static final String ERROR_CODE = "errorCode";
	public static final String USER_ID = "userId";
	public static final String CONTENT_TYPE = "contentType";
	public static final String AUTH_KEY = "Authorization";
	public static final String REQUEST_ID = "requestId";
		
	private AuditEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

