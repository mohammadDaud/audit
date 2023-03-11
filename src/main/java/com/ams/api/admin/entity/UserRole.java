package com.ams.api.admin.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ams.Utility.AppUtil;
import com.ams.api.admin.model.UserRoleCreationRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ROLE")
public class UserRole{
	

	public UserRole(UserRoleCreationRequest userRoleCreationRequest) {
		BeanUtils.copyProperties(userRoleCreationRequest, this);
	}
	

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_generator")
	@SequenceGenerator(name = "user_role_generator", sequenceName = "user_role_seq", allocationSize = 1)
	private long id;


	@JsonBackReference
	@OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER, orphanRemoval = true) // Orphan removal true to delete mapping on clear 
	List<UserRoleMenuMapping> assignedMenus = new ArrayList<>();

	@JsonBackReference
	@OneToMany(mappedBy = "userRole", cascade = CascadeType.MERGE)
	List<User> users = new ArrayList<>();

	@Column(nullable = false)
	private String roleName;

	
	private String description;

	@JsonIgnore
	private String createdBy;

	@JsonIgnore
	private LocalDateTime createdOn;

	@JsonIgnore
	private String modifiedBy;

	@JsonIgnore
	private LocalDateTime modifiedOn;
	
	
	@PrePersist
	public void createdAt() {
		this.createdOn = LocalDateTime.now();
		if(AppUtil.isUserLoggedIn())
			createdBy = getCurrentUser();
	}

	@PreUpdate
	public void updatedAt() {
		this.modifiedOn = LocalDateTime.now();
		this.modifiedBy = getCurrentUser();
	}

	private String getCurrentUser() {

		return ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();
	}
}
