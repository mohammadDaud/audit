package com.ams.api.admin.model;

import org.springframework.beans.BeanUtils;

import com.ams.Utility.DateUtils;
import com.ams.api.admin.entity.User;
import com.ams.model.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {

	public UserDTO(User user) {
		BeanUtils.copyProperties(user, this);
		this.userRole = user.getUserRole().getRoleName();
		this.createdOn = com.ams.Utility.DateUtils.convertDateToString(user.getCreatedOn());
		if (user.getModifiedOn() != null)
			this.modifiedOn = DateUtils.convertDateToString(user.getModifiedOn());
		
	}

	private String userRole;

	private String userId;

	private String userName;

	private Status userStatus;

	private boolean userBlocked;

	private String userMobile;

	private String userEmail;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;

}
