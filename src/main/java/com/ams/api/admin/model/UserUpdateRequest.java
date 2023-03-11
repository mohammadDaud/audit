package com.ams.api.admin.model;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateRequest {

		private long id;
		
	    private long userRole;

	    private long userType;
	        
	    private String userKey;
	    
	    private String userName;
	    
	    private String userDescription;
	    
	    private String userStatus;
	    
	    private boolean adminFlag;
	    
	    private boolean userBlocked;
	    
	    private boolean userCardMasking;
	    
	    private boolean userDashBoardFlag;
	    
	    private String userDashBoardMenu;
	    
	    private Long instId;
	    
	    private String userMobile;
	    
	    private String userEmail;
	    
	    private Long mcaGroup;
	    
	    private String mcaType;
	    
	    private boolean universal;
	    
	    private Long merchId;
		
		private boolean enableUserExpiry;
		
		private long userInactivePeriod;
		
		private boolean lockUser;
		
		private LocalTime workStartTime;

		private LocalTime workEndTime;
	  
}
