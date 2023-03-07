package com.maps.api.constants;

import oracle.security.crypto.util.InvalidFormatException;

public enum PaymentStatusEnum {

	AUTH_SUCCESS("AUTH_SUCCESS"),
	AUTH_FAILED("AUTH_FAILED"),
	PAYMENT_REQUEST_INITIATED("PAYMENT_REQUEST_INITIATED"),
	PAYMENT_REQUEST_RECEIVED("PAYMENT_REQUEST_RECEIVED"),
	SUCCESS("SUCCESS"),
	DENIED_RISK("DENIED_RISK"),
	RISK_PASSED("RISK_PASSED"),
	ALL("ALL");
	
	private String pmtStatus;
	
	public String getPmtStatus() {
		return pmtStatus;
	}

	PaymentStatusEnum(String pmtStatus) {
		this.pmtStatus=pmtStatus;
	}
	
	public static PaymentStatusEnum getValue(String pmtStatus) {
		  for(PaymentStatusEnum e: PaymentStatusEnum.values()) {
		    if(e.pmtStatus.equals(pmtStatus)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Payment Status"+" "+ pmtStatus); 
		}
	
}
