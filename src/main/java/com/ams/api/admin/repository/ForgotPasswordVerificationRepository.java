package com.ams.api.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ams.api.admin.entity.ForgotPasswordVerification;

public interface ForgotPasswordVerificationRepository extends JpaRepository<ForgotPasswordVerification, Long>,
						JpaSpecificationExecutor<ForgotPasswordVerification>  {
	
	@Query("select s.passedStageNo from ForgotPasswordVerification s where s.userId = ?1")
	Optional<Integer> findStageNoById(String userId);
	
	ForgotPasswordVerification findByUserId(String userId);
}