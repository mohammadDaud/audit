package com.maps.api.constants;

import oracle.security.crypto.util.InvalidFormatException;

public enum CardTypeEnum {
	MASTER("MASTER"),
	VISA("VISA"),
	AMEX("AMEX"),
	MADA("MADA"),
	ALL("ALL");
	
	private String card;
	public String getCard() {
		return card;
	}
	CardTypeEnum(String card) {
		this.card =card;
	}

	public static CardTypeEnum getValue(String card) {
		  for(CardTypeEnum e: CardTypeEnum.values()) {
		    if(e.card.equals(card)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Card Type"+" "+ card); 
		}
	
}
