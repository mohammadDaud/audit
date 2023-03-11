package com.ams.api.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ams.api.admin.entity.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long>,
						JpaSpecificationExecutor<UserLoginHistory>  {
	
	Optional<UserLoginHistory> findTop1ByUserUserIdOrderByIdDesc(String userId);
	
	@Query("select COUNT(h.id) from UserLoginHistory h where h.user.userId = :userId and (h.instId = :instId or null = :instId) and (h.merchId = :merchId or null = :merchId)")
	public int userIdCheck(@Param("userId") String user, @Param("instId") String instId, @Param("merchId") String merchId);
}