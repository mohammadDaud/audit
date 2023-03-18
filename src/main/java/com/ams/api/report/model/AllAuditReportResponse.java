package com.ams.api.report.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllAuditReportResponse {
	private List<AuditReportResponse> auditReportList;
}