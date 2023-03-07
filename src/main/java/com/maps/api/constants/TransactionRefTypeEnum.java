package com.maps.api.constants;

import oracle.security.crypto.util.InvalidFormatException;

public enum TransactionRefTypeEnum {

	PAYMENT_ID("PAYMENT_ID"),
	MERCHANT_REF_ID("MERCHANT_REF_ID"),
	TRANSACTION_ID("TRANSACTION_ID"),
	RRN("RRN"),
	ALL("ALL");
	
	private String tranRefType;

	TransactionRefTypeEnum(String tranRefType) {
		this.tranRefType=tranRefType;
	}

	public String getTranRefType() {
		return tranRefType;
	}
	
	public static TransactionRefTypeEnum getValue(String tranRefType) {
		  for(TransactionRefTypeEnum e: TransactionRefTypeEnum.values()) {
		    if(e.tranRefType.equals(tranRefType)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Transaction Reference Type"+" "+ tranRefType); 
		}


}