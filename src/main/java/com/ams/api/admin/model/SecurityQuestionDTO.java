package com.ams.api.admin.model;

import org.springframework.beans.BeanUtils;

import com.ams.api.admin.entity.SecurityQuestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityQuestionDTO {
	
	public SecurityQuestionDTO(SecurityQuestion	securityQuestion) {
			BeanUtils.copyProperties(securityQuestion, this);

		}
	private String question;
	
	private String answer;
	
}
