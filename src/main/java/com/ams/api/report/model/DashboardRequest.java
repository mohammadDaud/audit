package com.ams.api.report.model;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DashboardRequest {
	
	private String customDate;
	private String toDate;
	private String fromDate;
	private String merchantId;
	private String termId;
	private String cardHolderName;
	private String paymentMethod;
	private String cardNumberEnc;
	private String country;
	private String cardType;
	private String paymentScheme;
	private String invoicePayment;
	private String graphTypeFlag;
	private String graphUnitFlag;
	
}
