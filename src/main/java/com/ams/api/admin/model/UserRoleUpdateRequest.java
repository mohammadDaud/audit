package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRoleUpdateRequest {
	
    private String roleName;

    private String description;

}
