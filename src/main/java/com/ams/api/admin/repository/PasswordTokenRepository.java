package com.ams.api.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ams.api.admin.entity.PasswordResetToken;
import com.ams.constants.TokenType;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	@Query("select p from PasswordResetToken p where p.token = :token and p.tokenType = :tokenType")
	Optional<PasswordResetToken> findByToken(@Param("token") String token, @Param("tokenType") TokenType tokenType);
	
	@Query("select p from PasswordResetToken p where p.user.userId = :userId and p.tokenType = :tokenType")
	Optional<PasswordResetToken> findByUserId(@Param("userId") String userId, @Param("tokenType") TokenType tokenType);
}
