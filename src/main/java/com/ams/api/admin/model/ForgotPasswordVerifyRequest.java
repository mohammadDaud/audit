package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ForgotPasswordVerifyRequest {

	private String userId;
	private String instId;
	private String merchId;
}
