package com.ams.api.admin.entity;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseEntity {
	public String getCurrentUser() {

		return ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();
	}
}
