package com.ams.api.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.api.admin.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findAll();
	
	Optional<User> findByUserId(String userId);
	Optional<User> findByUserIdAndToken(String userId,String token);
	Optional<User> findByUserEmailIgnoreCase(String email);
	Optional<User> findByUserEmailIgnoreCaseAndUserId(String email, String userId);
	void deleteByUserId(String userId);
	
	
	
}