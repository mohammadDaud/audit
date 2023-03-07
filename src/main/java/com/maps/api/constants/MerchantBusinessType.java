package com.maps.api.constants;

public enum MerchantBusinessType {
	COMPANY("LLC"),
	INDIVIDUAL("INDIVIDUAL");

	private final String type;

	MerchantBusinessType(String type) {
		this.type = type;
	}

	public String getBusinessType() {
		return this.type;
	}
}
