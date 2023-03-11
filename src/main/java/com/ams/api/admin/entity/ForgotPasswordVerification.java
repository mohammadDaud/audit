package com.ams.api.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FORGOT_PASSWORD_VERIFICATION")
public class ForgotPasswordVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forgot_password_verify_generator")
	@SequenceGenerator(name = "forgot_password_verify_generator", sequenceName = "forgot_password_verify_seq", allocationSize = 1)
	private long id;

	private String userId;

	private Integer passedStageNo = 0;
}
