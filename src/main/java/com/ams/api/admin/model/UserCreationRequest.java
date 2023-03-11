package com.ams.api.admin.model;

import java.time.LocalTime;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCreationRequest {	
	
	    private long userRole;

	    private long userType;
	        
	    private String userKey;

	    private String userId;
	    
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
	    
//	    @UniqueUserEmail
	    private String userEmail;
	    
	    private String createdBy;
	    
	    private String modifiedBy;
	    
	    @Pattern(regexp = "(?=^.{8,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?&gt;.&lt;,])(?!.*\\s).*$",
	    		message = "Password must match password conditions")
	    private String password;
	    
	    private long mcaGroup;
	    
	    private String mcaType;
	    
	    private boolean universal;
	    
	    private Long merchId;
		
		private boolean enableUserExpiry;
		
		private long userInactivePeriod;
		
		private boolean lockUser;
		
		private LocalTime workStartTime;

		private LocalTime workEndTime;
		
		private String mode;
}
