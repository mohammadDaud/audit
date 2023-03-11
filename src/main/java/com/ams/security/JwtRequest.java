package com.ams.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	private String userId;
	private String password;
    private String recaptchaResponse;
}