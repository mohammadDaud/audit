package com.maps.api.constants;

public enum Duration {
	TODAY("TODAY"),
	LAST_WEEK("LAST_WEEK"), 
	LAST_THREE_MONTHS("LAST_THREE_MONTHS"),
	LAST_SIX_MONTHS("LAST_SIX_MONTHS"), 
	LAST_TWELVE_MONTHS("LAST_TWELVE_MONTHS"), 
	WEEK_TO_DATE("WEEK_TO_DATE"), 
	MONTH_TO_DATE("MONTH_TO_DATE"), 
	QUARTER_TO_DATE("QUARTER_TO_DATE"), 
	YEAR_TO_DATE("YEAR_TO_DATE"), 
	CUSTOM("CUSTOM"), 
	ALL_TIME("ALL_TIME");

	private final String date;

	Duration(String date) {
		this.date = date;
	}

	public String getValue() {
		return this.date;
	}
}
