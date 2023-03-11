package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerifyQuestionRequest {

	private String question;
	private String answer;
	private String userName;
}
