package com.ams.api.admin.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ams.api.admin.entity.PasswordHistory;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long>,
						JpaSpecificationExecutor<PasswordHistory>  {
	
	@Query("select u.password from PasswordHistory u where u.userId = ?1 ORDER BY u.id ASC")
	List<String> getPasswordsHistoryById(String userId);
	
	@Modifying
	@Transactional
	@Query("delete from PasswordHistory where password = ?1")
	void deletePassword(String password);
}