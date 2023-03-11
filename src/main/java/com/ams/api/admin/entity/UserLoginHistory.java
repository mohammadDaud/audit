package com.ams.api.admin.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "USER_LOGIN_HISTORY")
public class UserLoginHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_history_generator")
	@SequenceGenerator(name = "user_login_history_generator", sequenceName = "user_login_history_seq", allocationSize = 1)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	private LocalDateTime loggedInTime;

	private LocalDateTime loggedOutTime;
	
	private String instId;
	
	private String merchId;
}
