package com.ams.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
	private long roleId;
	private String roleName;
	private String mcaType;
	private long userType;
	private long instId;
	private String instIdStr;
	private long merchId;
	private String merchIdStr;
	private String institutionName;
	private String merchantName;
	private List<Map<String, String>> assignedMenu;
}