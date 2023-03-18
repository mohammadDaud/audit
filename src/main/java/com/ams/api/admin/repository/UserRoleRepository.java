package com.ams.api.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ams.api.admin.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

	List<UserRole> findAll();
	
	Optional<UserRole> findByRoleName(String roleName);
	Optional <UserRole> deleteByRoleName(String roleName);
}
