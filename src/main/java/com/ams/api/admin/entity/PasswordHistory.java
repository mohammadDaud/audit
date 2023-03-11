package com.ams.api.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PASSWORD_HISTORY")
public class PasswordHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_history_generator")
	@SequenceGenerator(name = "password_history_generator", sequenceName = "password_history_seq", allocationSize = 1)
	private long id;

	private String userId;

	private String password;
}
