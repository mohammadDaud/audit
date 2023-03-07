package com.maps.common.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRiskDTO {

	private BigDecimal tranAmt; // Transaction Amount
	private BigDecimal accumulatedTranAmt; // Accumulated Transaction Amount
	private long accumulatedTranCount; // Accumulated Transaction Count
	private short actionCode;
}