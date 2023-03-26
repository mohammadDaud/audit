package com.ams.api.constants;

public enum AuditMatrix {

	ISSUE_ADD("ISSUE","ADD"),
	ISSUE_UPDATE("ISSUE","UPDATE"),
	ISSUE_DELETE("ISSUE","DELETE");

	private String module;
	private String action;
	
	AuditMatrix(String module, String action) {
		this.module = module;
		this.action = action;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}