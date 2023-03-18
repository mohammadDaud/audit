package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCreationRequest {	
	
	    private String userRole;

	    private String userId;
	    
	    private String userName;
	    
	    private String userStatus;
	    
	    private boolean userBlocked;
	    
	    private String userMobile;
	    
	    private String userEmail;
	    
	    private String password;
	    
}
