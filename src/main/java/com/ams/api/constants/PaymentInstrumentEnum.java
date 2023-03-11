package com.ams.api.constants;

import oracle.security.crypto.util.InvalidFormatException;

public enum PaymentInstrumentEnum {

	CARD("CARD"),
	APPLE_PAY("APPLE_PAY"),
	ALL("ALL");
	
	private String paymentInst;
	
	public String getPaymentInst() {
		return paymentInst;
	}

	PaymentInstrumentEnum(String paymentInst) {
		this.paymentInst =paymentInst;
	}
	

	public static PaymentInstrumentEnum getValue(String paymentInst) {
		  for(PaymentInstrumentEnum e: PaymentInstrumentEnum.values()) {
		    if(e.paymentInst.equals(paymentInst)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Payment Instrument"+" "+ paymentInst); 
		}
}

