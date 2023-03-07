package com.maps.api.constants;

public enum AuditMatrix {

	USER_ADD("USER","USER_DETAILS", "ADD"),
	USER_UPDATE("USER","USER_DETAILS", "UPDATE"),
	USER_DELETE("USER","USER_DETAILS", "DELETE");

	private String menuKey;
	private String table;
	private String action;
	
	AuditMatrix(String menuKey, String table, String action) {
		this.menuKey = menuKey;
		this.table = table;
		this.action = action;
	}

	public String menuKey() {
		return this.menuKey;
	}

	public String table() {
		return this.table;
	}

	public String action() {
		return this.action;
	}
}