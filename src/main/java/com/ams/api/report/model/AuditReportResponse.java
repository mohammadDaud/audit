package com.ams.api.report.model;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuditReportResponse {
	
//	public AuditReportResponse(AuditTrailLog auditTrailLog){
//		BeanUtils.copyProperties(auditTrailLog, this);		
//	}
	
	private String moduleName;

	private String action;

	private String tableName;

	private long tableKey;

	private String oldValues;

	private String newValues;

	private String createdBy;

	private LocalDateTime createdOn;

	private String loggedIp;
}