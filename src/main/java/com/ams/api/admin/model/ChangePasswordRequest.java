package com.ams.api.admin.model;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {

	private String userId;
	private String token;
	private String oldPassword;
	 @Pattern(regexp = "(?=^.{8,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?&gt;.&lt;,])(?!.*\\s).*$",
	    		message = "Password must match password conditions")
	private String newPassword;
	private String confirmPassword;
}
