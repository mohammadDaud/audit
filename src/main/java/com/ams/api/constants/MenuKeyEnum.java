package com.ams.api.constants;

public enum MenuKeyEnum {

	USER("USER"),
	USER_ROLE("USER_ROLE"),
	MENU ("MENU");
	
	private String menuKey;
	
	MenuKeyEnum(String menuKey){
		this.menuKey = menuKey;
	}
	
	public String menuKey() {
		return menuKey;
	}
}