package com.maps.api.constants;

public enum TransactionStatusEnum {
	SCHEDULED("SCHEDULED"), PROCESSED("PROCESSED"), FAILED("FAILED"), SUCCESS("SUCCESS");

	private final String status;

	TransactionStatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
