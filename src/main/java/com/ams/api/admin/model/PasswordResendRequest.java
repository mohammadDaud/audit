package com.ams.api.admin.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordResendRequest {
	
	private long[] userItems;
}
