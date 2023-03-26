package com.ams.common.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ams.common.entity.AuditTrailLog;

public interface AuditRepository extends JpaRepository<AuditTrailLog, Long>,JpaSpecificationExecutor<AuditTrailLog> {

	List<AuditTrailLog> findByCreatedOnBetween(LocalDateTime fromDate, LocalDateTime toDate);

}