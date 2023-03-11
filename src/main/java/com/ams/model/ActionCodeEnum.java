package com.ams.model;

import oracle.security.crypto.util.InvalidFormatException;

public enum ActionCodeEnum {
	AUTHORIZATION("AUTHORIZATION"),
	CAPTURE("CAPTURE"),
	PURCHASE("PURCHASE"),
	CREDIT("CREDIT"),
	INQUIRY("INQUIRY"),
	VOID_AUTHORIZATION("VOID AUTHORIZATION"),
	VOID_CAPTURE("VOID CAPTURE"),
	VOID_CREDIT("VOID CREDIT"),
	VOID_PURCHASE("VOID PURCHASE"),
	NOTIFICATION("NOTIFICATION"),
	ALL("ALL");
	
	private String actionCode;

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	ActionCodeEnum(String actionCode) {
		this.actionCode =actionCode;
	}

	public static ActionCodeEnum getValue(String actionCode) {
		  for(ActionCodeEnum e: ActionCodeEnum.values()) {
		    if(e.actionCode.equals(actionCode)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Action Code"+" "+ actionCode); 
		}
}
