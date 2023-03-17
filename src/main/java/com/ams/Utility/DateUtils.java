package com.ams.Utility;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DateUtils {
	public static DateTimeFormatter yyyyMMddhhssmma = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
	public static DateTimeFormatter MMDDHHmmss = DateTimeFormatter.ofPattern("MMddHHmmss");
	public static DateTimeFormatter YYMMMDDHHMMSSA = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss a");

	public static String convertDateToString(LocalDateTime localDateTime) {

		if(Objects.isNull(localDateTime)) 
			return null;
		
		return convertDateToString(localDateTime, yyyyMMddhhssmma);
	}

	public static String convertDateToString(LocalDateTime localDateTime, DateTimeFormatter formatter) {

		if(Objects.isNull(localDateTime)) 
			return null;
			
		return localDateTime.format(formatter);
	}

	public static String getCurrentDateTime() {

		return LocalDateTime.now().format(MMDDHHmmss);
	}

	public static String convertDateToString(OffsetDateTime gmtDateTime, DateTimeFormatter formatter) {
		if(Objects.isNull(gmtDateTime)) 
			return null;
		return gmtDateTime.format(formatter);
	}

	public static String getCurrentDateTime(DateTimeFormatter formater) {

		return OffsetDateTime.now().format(formater);
	}

	public static LocalDateTime getCurrentUTCTime() {
		return OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime();
	}

	public static int dayOfYear() {
		return LocalDate.now().getDayOfYear();
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}

		return DateUtils.convertDateToString(new java.sql.Timestamp(date.getTime()).toLocalDateTime(),
				DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a"));

	}

	public static String formatDate(LocalDateTime date) {
		if (date == null) {
			return "";
		}
		return DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a").format(date);
	}

	public static LocalDateTime convertStringtoLocalDateTime(String date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
	}
	
	public static LocalDate convertStringtoLocalDate(String date) {
		if (date == null) {
			return null;
		}
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE); //yyyy-mm-dd
	}

	public static int getCurrentYear() {
		return LocalDate.now().getYear();
	}

	public static List<Integer> getNextNYear(int number) {
		List<Integer> nextNYears = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			nextNYears.add(getCurrentYear() + i);
		}
		return nextNYears;
	}
}