package com.ams.api.constants;

public enum ErrorCodeEnum {

	FORBIDDEN_ERROR("LOG-403"),
	APPLICATION_ERROR("LOG-300"),
	INVALID_METHOD_ARGUMENT("LOG-600"),
	BAD_CREDENTIALS_ERROR("LOG-400"), 
	DATA_INTEGRITY_ERROR("LOG-117"),
	RESOURCE_NOT_FOUND("LOG-404"),
	PROCESSING_ERROR("LOG-100"),
	JWT_EXPIRED("LOG-401");

	private final String errorCode;

	private ErrorCodeEnum(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}
}