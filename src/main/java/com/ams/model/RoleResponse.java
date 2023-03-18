package com.ams.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {
	private String roleName;
	private List<Map<String, String>> assignedMenu;
}