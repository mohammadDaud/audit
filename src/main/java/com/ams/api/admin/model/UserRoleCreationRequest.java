package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRoleCreationRequest {
	
    private String roleName;

    private String description;

    private long userType;
    
    private long instId;
    
    private Long merchantId;

    private String createdBy;
    
    private String updatedBy;
}
