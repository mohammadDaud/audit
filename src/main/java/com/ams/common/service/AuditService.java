package com.ams.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ams.Utility.AppUtil;
import com.ams.Utility.JsonUtil;
import com.ams.api.constants.AuditMatrix;
import com.ams.api.report.model.AuditReportRequest;
import com.ams.common.entity.AuditTrailLog;
import com.ams.common.repository.AuditRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuditService {

	private final AuditRepository auditRepository;

	private final HttpServletRequest request;

	public List<AuditTrailLog> getAuditTrailLog(AuditReportRequest auditReportRequest) {

		AuditTrailLog auditTrailLog = new AuditTrailLog();
		auditTrailLog.setModuleName(
				!Strings.isEmpty(auditReportRequest.getModuleName()) ? auditReportRequest.getModuleName() : null);
		auditTrailLog
				.setAction(!Strings.isEmpty(auditReportRequest.getAction()) ? auditReportRequest.getAction() : null);
		auditTrailLog.setModuleName(
				!Strings.isEmpty(auditReportRequest.getTableName()) ? auditReportRequest.getTableName() : null);

		Example<AuditTrailLog> example = Example.of(auditTrailLog, ExampleMatcher.matchingAll().withIgnoreNullValues()
				.withIgnoreCase().withIgnorePaths("id"));

		return auditRepository.findAll(getSpecFromDatesAndExample(auditReportRequest.getFromDate().atStartOfDay(),
				auditReportRequest.getToDate().atTime(23, 59, 59), example), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
	}

	public Specification<AuditTrailLog> getSpecFromDatesAndExample(LocalDateTime from, LocalDateTime to,
			Example<AuditTrailLog> example) {
		return (Specification<AuditTrailLog>) (root, query, builder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (from != null) {
				predicates.add(builder.greaterThan(root.get("createdOn"), from));
			}
			if (to != null) {
				predicates.add(builder.lessThan(root.get("createdOn"), to));
			}
			predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	};

/////////////////////////

//	public void logAddAudit(String id, Object newObject, AuditMatrix auditMatrix) {
//		log.info(":::::Audit Service for Add check flag:::::");
//		if (!this.isAuditEnabled(auditMatrix.module()))
//			return;
//
//		AuditTrailLog auditEntity = getAuditEntity(id, newObject, auditMatrix, null);
//		if (auditEntity != null)
//			this.logAudit(auditEntity);
//
//	}

	private AuditTrailLog getAuditEntity(String id, Object newObject, Object oldObject, AuditMatrix auditMatrix) {

		AuditTrailLog auditTrailLog = new AuditTrailLog();
		auditTrailLog.setModuleName(auditMatrix.getModule());
		auditTrailLog.setAction(auditMatrix.getAction());
		String user = AppUtil.getCurrentUser();
		auditTrailLog.setCreatedBy(user);
		auditTrailLog.setCreatedOn(LocalDateTime.now());
		auditTrailLog.setEntityId(id);
		auditTrailLog.setNewValues(JsonUtil.toPrettyString(newObject));
		auditTrailLog.setOldValues(JsonUtil.toPrettyString(oldObject));
		auditTrailLog.setLoggedIp(AppUtil.getClientIpAddr(request));
		return auditTrailLog;
	}

	public void logAudit(String id, Object oldObject, Object newObject, AuditMatrix auditMatrix) {

		log.info(":::::Audit Service :::::");
		AuditTrailLog auditEntity = getAuditEntity(id, newObject, oldObject, auditMatrix);
		if (Objects.nonNull(auditEntity))
			auditRepository.save(auditEntity);
	}
}