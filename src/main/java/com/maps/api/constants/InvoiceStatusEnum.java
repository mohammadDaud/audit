package com.maps.api.constants;

public enum InvoiceStatusEnum {
	DRAFT("DRAFT"), SENT("SENT"), DUE("DUE"), 
	EXPIRED("EXPIRED");

	private final String status;

	InvoiceStatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
