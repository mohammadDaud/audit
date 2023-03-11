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
		this.approvalStatus = "APPROVED";

		// this.assignedMenuId = userRole.getAssignedMenus().stream().map((menuMapped)->
		// menuMapped.getMenu().getId()).collect(Collectors.toList());

		this.assignedMenuId = userRole.getAssignedMenus().stream().map((menuMapped) -> {
			// return hashmap for each menu access
			Map<Long, String> hashMap = new HashMap<>();
			hashMap.put(menuMapped.getMenu().getId(), menuMapped.getAction());
			return hashMap;
		}).collect(Collectors.toList());

		this.createdOn = DateUtils.convertDateToString(userRole.getCreatedOn());
		if (userRole.getModifiedOn() != null)
			this.modifiedOn = DateUtils.convertDateToString(userRole.getModifiedOn());

	}

	private long id;

	private String roleName;

	private String description;

	private String userType;

	private long userTypeId;

	private long instId;

	private Long merchantId;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;

	private List<Map<Long, String>> assignedMenuId;

	private String approvalStatus;

	private String action;

}
