package com.ams.api.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ams.api.admin.model.UserCreationRequest;
import com.ams.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DETAILS")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	public User(UserCreationRequest userCreationRequest) {
		BeanUtils.copyProperties(userCreationRequest, this);
		this.setPassword(new BCryptPasswordEncoder().encode(userCreationRequest.getPassword()));
	}
	public User(User oldUser) {
		BeanUtils.copyProperties( oldUser, this);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_generator")
	@SequenceGenerator(name = "user_details_generator", sequenceName = "user_details_seq", allocationSize = 1)
	private long id;

	@ManyToOne()
	@JoinColumn(name = "userRoleId")
	private UserRole userRole;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String userName;

	private String userDescription;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status userStatus;

	private Boolean userBlocked;

	private String userMobile;

	private String userEmail;

	private String token;
	
	private String createdBy;

	private LocalDateTime createdOn;

	private String modifiedBy;

	private LocalDateTime modifiedOn;

	private Date tokenExpiryDate;

	private String password;
	
	private Boolean lockUser;
	
}
