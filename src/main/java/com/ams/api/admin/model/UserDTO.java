package com.ams.api.admin.model;

import java.time.LocalTime;

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
		this.userRoleId = user.getUserRole().getId();
		this.approvalStatus = "APPROVED";
		this.createdOn = com.ams.Utility.DateUtils.convertDateToString(user.getCreatedOn());
		if (user.getModifiedOn() != null)
			this.modifiedOn = DateUtils.convertDateToString(user.getModifiedOn());
		
	}

	private long id;

	private long userRoleId;

	private String userRole;

	private long userTypeId;

	private String userType;

	private String userKey;

	private String userId;

	private String userName;

	private String userDescription;

	private Status userStatus;

	private boolean adminFlag;

	private String approvalStatus;

	private boolean userBlocked;

	private boolean userCardMasking;

	private boolean userDashBoardFlag;

	private String userDashBoardMenu;

	private long instId;
	private String instName;

	private String userMobile;

	private String userEmail;

	private String createdBy;

	private String createdOn;

	private String modifiedBy;

	private String modifiedOn;

	private long mcaGroup;

	private String mcaGroupName;

	private String mcaType;

	private Boolean universal;

	private String action;
	
	private long merchId;
	private String merchName;
	
	private Boolean enableUserExpiry;
	
	private Long userInactivePeriod;
	
	private Boolean lockUser;
	
	private LocalTime workStartTime;

	private LocalTime workEndTime;
}
