package com.ams.api.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
	private long id;
	private String tranDate;
	private Double amount;
	private String cardType;
	private String pmtStatus;
	private String paymentId;
}
