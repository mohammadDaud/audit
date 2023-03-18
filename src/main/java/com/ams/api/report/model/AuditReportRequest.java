package com.ams.api.report.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuditReportRequest {
	
	@NotNull(message = "Please provide From Date")
	private LocalDate fromDate;
	
	@NotNull(message = "Please provide To Date")
	private LocalDate toDate;
	
	private String action;
	
	private String tableName;
	
	private String moduleName;
	
	private String instId;
	
	private String merchId;
}