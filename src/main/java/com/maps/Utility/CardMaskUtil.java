package com.maps.Utility;

import java.util.stream.IntStream;

public class CardMaskUtil {
	
	public static String maskCardNumber(String cardNumber, int startLength, int endLength, String symbol) {
		int maskedLength = (cardNumber.length() - (startLength + endLength));
		StringBuilder stringBuilder = new StringBuilder();
		IntStream.range(0, maskedLength).forEach(length -> stringBuilder.append(symbol));
		return cardNumber.substring(0, startLength) + stringBuilder + cardNumber.substring(cardNumber.length() - endLength, cardNumber.length());
	}	

}

