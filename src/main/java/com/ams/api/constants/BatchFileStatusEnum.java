package com.ams.api.constants;

public enum BatchFileStatusEnum {
	UPLOADED("UPLOADED"), PROCESSED("PROCESSED"), FAILED("FAILED");

	private final String status;

	BatchFileStatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
