package com.ams.api.report.model;

import com.ams.Utility.DateUtils;
import com.ams.api.admin.entity.UserLoginHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginHistoryResponse {
	
	public UserLoginHistoryResponse(UserLoginHistory userLoginHistory){
		this.userId = userLoginHistory.getUser().getUserId();
		this.userName = userLoginHistory.getUser().getUserName();
		this.loggedInTime = DateUtils.convertDateToString(userLoginHistory.getLoggedInTime());
		
		if(userLoginHistory.getLoggedOutTime() != null)
			this.loggedOutTime = DateUtils.convertDateToString(userLoginHistory.getLoggedOutTime());
	}
	private String userId;
	
	private String userName;
		
	private String loggedInTime;
	
	private String loggedOutTime;
}