package com.ams.api.admin.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllMenuGroupResponse {
	private List<MenuGroup> menuGroupList;
}
