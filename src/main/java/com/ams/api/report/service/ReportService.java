package com.ams.api.report.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.logging.log4j.util.Strings;
import org.kie.api.runtime.manager.audit.AuditService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ams.api.admin.entity.User;
import com.ams.api.admin.entity.UserLoginHistory;
import com.ams.api.admin.repository.UserLoginHistoryRepository;
import com.ams.api.report.model.UserLoginHistoryRequest;
import com.ams.common.service.MessageSourceService;
import com.ams.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final UserLoginHistoryRepository userLoginHistoryRepository;
	private final MessageSourceService messageSource;

	public List<UserLoginHistory> getUserLoginHistory(UserLoginHistoryRequest request) {

		if(!findUser(request)) {
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.NOT_FOUND, "User"));
		}
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		User u = new User();
		u.setUserId(!Strings.isEmpty(request.getUserId()) ? request.getUserId() : null);
		u.setUserName(!Strings.isEmpty(request.getUserName()) ? request.getUserName() : null);
		userLoginHistory.setUser(u);
		
		Example<UserLoginHistory> example = Example.of(userLoginHistory, ExampleMatcher.matchingAll()
				.withIgnoreNullValues().withIgnoreCase().withIgnorePaths("id", "user.id", "user.hiddenFlag", "instId", "merchId"));

		return userLoginHistoryRepository.findAll(getSpecFromDatesAndExample(request.getFromDate().atStartOfDay(),
										request.getToDate().atTime(23, 59, 59), example, request.getInstId(), request.getMerchId()),
										Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
	
	}
	public Specification<UserLoginHistory> getSpecFromDatesAndExample(
			  LocalDateTime from, LocalDateTime to, Example<UserLoginHistory> example,
			  String instId, String merchId) {

			    return (Specification<UserLoginHistory>) (root, query, builder) -> {
			         final List<Predicate> predicates = new ArrayList<>();

			         if (from != null) {
			            predicates.add(builder.greaterThan(root.get("loggedInTime"), from));
			         }
			         if (to != null) {
			            predicates.add(builder.lessThan(root.get("loggedInTime"), to));
			         }
			         if(instId != null) {
			        	 predicates.add(builder.equal(root.get("instId"), instId));
			         }
			         if(merchId != null) {
			        	 predicates.add(builder.equal(root.get("merchId"), merchId));
			         }
			         predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

			         return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			    };
			};

//	public List<AuditTrailLog> getAuditTrails(AuditReportRequest request) {	
//		return auditService.getAuditTrailLog(request);
//	}
	public Boolean findUser(UserLoginHistoryRequest request) {
		int i = userLoginHistoryRepository.userIdCheck(request.getUserId(),request.getInstId(), request.getMerchId());
		return i > 0;
	}
}