package com.ams.api.admin.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.ams.Utility.DateUtils;
import com.ams.api.admin.entity.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRoleDTO {

	public UserRoleDTO(UserRole userRole) {
		BeanUtils.copyProperties(userRole, this);

		this.assignedMenuId = userRole.getAssignedMenus().stream().map((menuMapped) -> {
			// return hashmap for each menu access
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put(menuMapped.getMenu().getKey(), menuMapped.getAction());
			return hashMap;
		}).collect(Collectors.toList());

		this.createdOn = DateUtils.convertDateToString(userRole.getCreatedOn());
		if (userRole.getModifiedOn() != null)
			this.modifiedOn = DateUtils.convertDateToString(userRole.getModifiedOn());

	}

	private String roleName;

	private String description;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;

	private List<Map<String, String>> assignedMenuId;

}
