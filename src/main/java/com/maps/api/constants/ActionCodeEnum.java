package com.maps.api.constants;

public enum ActionCodeEnum {
	PURC("PURC"), REVERSAL("REVERSAL"), INQ("INQ"), 
	VAUTH("VAUTH"), VCAPT("VCAPT"), VPURC("VPURC"), VCRED("VCRED"), NOTF("NOTF");

	private final String id;

	ActionCodeEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
