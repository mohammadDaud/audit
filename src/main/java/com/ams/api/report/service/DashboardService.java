//package com.ams.api.report.service;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.WeekFields;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import org.springframework.stereotype.Service;
//
//import com.ams.Utility.AppUtil;
//import com.ams.api.report.model.DashboardCountVolumeResponse;
//import com.ams.api.report.model.DashboardRequest;
//import com.ams.common.service.MessageSourceService;
//import com.ams.exception.ApplicationException;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class DashboardService {
//
//	private final MessageSourceService messageSource;
////	
////	public List<DashboardResponse> getAllTransactionRecords(DashboardRequest dashboardRequest) {
////		dashboardRequest.setCardNumberEnc(encryptText(dashboardRequest.getCardNumberEnc()));
////		return transactionepository.getAllTransactionList(dashboardRequest);
////	}
//
//	public List<DashboardCountVolumeResponse> getAllTransactionCount(DashboardRequest dashboardRequest) {
//		dashboardRequest.setCardNumberEnc(encryptText(dashboardRequest.getCardNumberEnc()));
//		return transactionepository.getCountAmount(dashboardRequest);
//	}
//	
//
//	public List<GraphUnitTypeResponse> getChartCountAndAmount(DashboardRequest dashboardRequest) {
//		dashboardRequest.setCardNumberEnc(encryptText(dashboardRequest.getCardNumberEnc()));
//		return transactionepository.getChartData(dashboardRequest);
//	}
//	
//	
//
//	private String encryptText(String cardNumber) {
//		if(cardNumber ==null || cardNumber.isEmpty()) {
//			return "";
//		}
//		try {
//			return encryptionService.getTextEncryptedWithDEK(cardNumber.toCharArray(), AppUtil.getCurrentInstIdStr());
//		} catch (Exception e) {
//			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ENCRYPTION, "card number"), e);
//		}
//		
//	}
//	
//
//
//	public List<GraphUnitTypeResponse> getLast15Month(String format) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
//		return IntStream.range(0, 15)
//		 .mapToObj( i ->  new GraphUnitTypeResponse(formatter.format(YearMonth.now().minusMonths(i)).toUpperCase())	)
//		.collect(Collectors.toList());
//		
//	}
//	public List<GraphUnitTypeResponse> getLast15Year(String format) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
//		return IntStream.range(0, 15)
//		 .mapToObj( i ->  new GraphUnitTypeResponse(formatter.format(YearMonth.now().minusYears(i)).toUpperCase())	)
//		.collect(Collectors.toList());
//		
//	}
//	public List<GraphUnitTypeResponse> getLast15Day(String format) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
//		return IntStream.range(0,15)
//				 .mapToObj( i ->  new GraphUnitTypeResponse(formatter.format(LocalDate.now().minusDays(i)).toUpperCase()
//						))
//				.collect(Collectors.toList());
//		
//	}
//	
//	public List<GraphUnitTypeResponse> getLast15Week(String format) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
//		List<GraphUnitTypeResponse> collect = IntStream.range(0, 15)
//		 .mapToObj( i ->  new GraphUnitTypeResponse(LocalDate.now().minusWeeks(i).get(WeekFields.SUNDAY_START.weekOfMonth()) +"-" + 
//				 										formatter.format(LocalDate.now().minusWeeks(i)).toUpperCase())	)
//		.collect(Collectors.toList());
//		
//		
//		return collect;
//	}
//	public List<GraphUnitTypeResponse> getLast15HR(){
//		List<GraphUnitTypeResponse> collect =  IntStream.range(1,16)
//				 .mapToObj( i ->  new GraphUnitTypeResponse(String.valueOf(i)))
//						.collect(Collectors.toList());
//		
//		return collect;
//	}
//}