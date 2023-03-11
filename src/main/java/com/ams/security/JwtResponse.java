package com.ams.security;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private String jwttoken;
	private String userId;
	@JsonRawValue
	private List<String> roles;
	private Boolean isSecurityQuestionExists;

}