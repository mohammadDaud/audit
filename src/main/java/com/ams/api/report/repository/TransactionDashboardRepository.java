//package com.ams.api.report.repository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import com.maps.Utility.AppUtil;
//import com.maps.api.constants.Duration;
//import com.maps.api.transaction.report.model.DashboardCountVolumeResponse;
//import com.maps.api.transaction.report.model.DashboardRequest;
//import com.maps.api.transaction.report.model.DashboardResponse;
//import com.maps.api.transaction.report.model.GraphUnitTypeResponse;
//
//import io.micrometer.core.instrument.util.StringUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Repository
//@RequiredArgsConstructor
//public class TransactionDashboardRepository {
//
//	private final JdbcTemplate jdbcTemplate;
//	private HashMap<String,String> map = new HashMap<>(); 
//
//	public String getSearchQuery(DashboardRequest dRequest, String requestFlag) {
//		if(dRequest.getCustomDate() != null)
//		map.put("CUSTOM_DATE", dRequest.getCustomDate());
//		List<Object> param= new ArrayList<>();
//		String selectAllQuery = "";
//		if(requestFlag.equals("ALL")) {
//			selectAllQuery = DashboardQuery.getAllRecords;
//			}
//		else if(requestFlag.equals("COUNT")) {
//		selectAllQuery = DashboardQuery.getCountAmountWithPaymentStatus;
//			}
//		
//		StringBuffer whereBuffer = new StringBuffer("");
//		String columnName= map.get("CUSTOM_DATE");
//		if(columnName.equals(Duration.TODAY.toString())) whereBuffer.append(" = trunc(sysdate)");
//		else if (columnName.equals(Duration.LAST_WEEK.toString())) whereBuffer.append(" >= trunc(sysdate)-7");
//		else if (columnName.equals(Duration.LAST_THREE_MONTHS.toString())) whereBuffer.append(" >= trunc(sysdate)-91");
//		else if (columnName.equals(Duration.LAST_SIX_MONTHS.toString())) whereBuffer.append(" >= trunc(sysdate)-182");
//		else if (columnName.equals(Duration.LAST_TWELVE_MONTHS.toString())) whereBuffer.append(" >= trunc(sysdate)-365");
//		else if (columnName.equals(Duration.WEEK_TO_DATE.toString())) whereBuffer.append(" >= trunc((sysdate),'day')");
//		else if (columnName.equals(Duration.MONTH_TO_DATE.toString())) whereBuffer.append(" >= trunc((sysdate),'month')");
//		else if (columnName.equals(Duration.QUARTER_TO_DATE.toString())) whereBuffer.append(" >= trunc((sysdate),'Q')");
//		else if (columnName.equals(Duration.YEAR_TO_DATE.toString())) whereBuffer.append(" >= trunc((sysdate),'year')");
//		else if (columnName.equals(Duration.ALL_TIME.toString())) whereBuffer.append(" = TRUNC(TRAN_DATE)");
//		else if (columnName.equals(Duration.CUSTOM.toString())) {
//			whereBuffer.append(" between ? AND ? And merchant_id = '" +AppUtil.getCurrentMerchantIdStr()+"'");
//			param.add(dRequest.getFromDate());
//			param.add(dRequest.getToDate());
//		}
//		
//		whereBuffer.append(" AND MERCHANT_ID = '"+AppUtil.getCurrentMerchantIdStr()+"'");
//		
//		if (!StringUtils.isBlank(dRequest.getCardHolderName())) {
//			whereBuffer.append(" AND CARD_HOLDER_NAME = ? ");
//			param.add(dRequest.getCardHolderName());
//		}
//		if (!StringUtils.isBlank(dRequest.getCardNumberEnc())) {
//			whereBuffer.append(" AND CARD_NUMBER_ENC = ?");
//			param.add(dRequest.getCardNumberEnc());
//		}
//		if (!StringUtils.isBlank(dRequest.getCardType())) {
//			whereBuffer.append(" AND CARD_TYPE = ? ");
//			param.add(dRequest.getCardType());
//		}
//		if (!StringUtils.isBlank(dRequest.getPaymentScheme())) {
//			whereBuffer.append(" AND PAYMENT_SCHEME =?");
//			param.add(dRequest.getPaymentScheme());
//		}
//		if (!StringUtils.isBlank(dRequest.getTermId())) {
//			whereBuffer.append(" AND TERM_ID = ?");
//			param.add(dRequest.getTermId());
//		}
//		if (!StringUtils.isBlank(dRequest.getCountry())) {
//			whereBuffer.append(" AND COUNTRY = ?");
//			param.add(dRequest.getCountry());
//		}
//		if (!StringUtils.isBlank(dRequest.getPaymentMethod())) {
//			whereBuffer.append(" AND PAYMENT_METHOD = ?");
//			param.add(dRequest.getPaymentMethod());
//		}
//		if (!StringUtils.isBlank(dRequest.getInvoicePayment())) {
//			whereBuffer.append(" AND INVOICE_PAYMENT=?");
//			param.add(dRequest.getInvoicePayment());
//		}
//		
//		if(requestFlag.equals("ALL")) {
//			
//		whereBuffer.append(" AND MERCHANT_ID = '"+AppUtil.getCurrentMerchantIdStr()+"'  order by tran_date desc fetch first 20 rows only");
//		}
//		
//		else if(requestFlag.equals("COUNT")) {
//			log.debug("COUNT QUERY=== " + selectAllQuery.replaceAll("%s", whereBuffer.toString()));
//			return selectAllQuery.replaceAll("%s", whereBuffer.toString());
//		}
//	
//		selectAllQuery += whereBuffer;
//		log.debug("LIST QUERY=== " + selectAllQuery);
//		return selectAllQuery;
//
//	}
//	
//	private List<Object> getFilterParameter(DashboardRequest dashBoardRequest, String requestFlag) {
//		List<Object> param= new ArrayList<>();
//		if (dashBoardRequest.getCustomDate().equals(Duration.CUSTOM.toString()) && requestFlag.equals("COUNT")) {
//			param.add(dashBoardRequest.getFromDate());
//			param.add(dashBoardRequest.getToDate());
//			param.add(dashBoardRequest.getFromDate());
//			param.add(dashBoardRequest.getToDate());
//			param.add(dashBoardRequest.getFromDate());
//			param.add(dashBoardRequest.getToDate());
//		}
//		else if (dashBoardRequest.getCustomDate().equals(Duration.CUSTOM.toString())){
//			param.add(dashBoardRequest.getFromDate());
//			param.add(dashBoardRequest.getToDate());
//		}
//		if(!StringUtils.isBlank(dashBoardRequest.getCardHolderName())) param.add(dashBoardRequest.getCardHolderName());
//		if(!StringUtils.isBlank(dashBoardRequest.getCardNumberEnc())) param.add(dashBoardRequest.getCardNumberEnc());
//		if(!StringUtils.isBlank(dashBoardRequest.getCardType())) param.add(dashBoardRequest.getCardType());
//		if(!StringUtils.isBlank(dashBoardRequest.getCountry())) param.add(dashBoardRequest.getCountry());
//		if(!StringUtils.isBlank(dashBoardRequest.getInvoicePayment())) param.add(dashBoardRequest.getInvoicePayment());
//		if(!StringUtils.isBlank(dashBoardRequest.getTermId())) param.add(dashBoardRequest.getTermId());
//		if(!StringUtils.isBlank(dashBoardRequest.getMerchantId())) param.add(dashBoardRequest.getMerchantId());
//		if(!StringUtils.isBlank(dashBoardRequest.getPaymentScheme())) param.add(dashBoardRequest.getPaymentScheme());
//		if(!StringUtils.isBlank(dashBoardRequest.getPaymentMethod())) param.add(dashBoardRequest.getPaymentMethod());
//		return param;
//	}
//
//
//	public List<DashboardResponse> getAllTransactionList(DashboardRequest transactionReport){
//		String searchQuery = getSearchQuery(transactionReport, "ALL");
//		return jdbcTemplate.query(searchQuery, mapResults(), getFilterParameter(transactionReport, "ALL").toArray());
//	}
//	
//	public List<DashboardCountVolumeResponse> getCountAmount(DashboardRequest transactionReport){
//		String searchQuery = getSearchQuery(transactionReport, "COUNT");
//		return jdbcTemplate.query(searchQuery, mapCountResults(), getFilterParameter(transactionReport, "COUNT").toArray());
//	}
//	
//	public List<GraphUnitTypeResponse> getChartData(DashboardRequest transactionReport){
//		String searchQuery = getSearchQueryForChart(transactionReport);
//		return jdbcTemplate.query(searchQuery, mapChartResults(), getFilterParameter(transactionReport, "CHART").toArray());
//	}
//
//	private RowMapper<DashboardResponse> mapResults(){
//
//		return (rs, rowNum)->{
//			return DashboardResponse.builder()
//					.id(rs.getLong("ID"))
//					.tranDate(rs.getString("TRAN_DATE"))
//					.amount(rs.getDouble("AMOUNT"))
//					.cardType(rs.getString("CARD_TYPE"))
//					.pmtStatus(rs.getString("PMT_STATUS"))
//					.paymentId(rs.getString("PAYMENT_ID"))
//					.build();
//		};
//
//	}
//	
//	private RowMapper<DashboardCountVolumeResponse> mapCountResults(){
//
//		return (rs, rowNum)->{
//			return DashboardCountVolumeResponse.builder()
//					.count(rs.getString("COUNT"))
//					.category(rs.getString("CATEGORY"))
//					.status(rs.getString("STATUS"))
//					.volume(rs.getDouble("VOLUME"))
//					.build();
//		};
//
//	}
//	
//	private RowMapper<GraphUnitTypeResponse> mapChartResults(){
//
//		return (rs, rowNum)->{
//			return GraphUnitTypeResponse.builder()
//					.count(rs.getInt("COUNT"))
//					.volume(rs.getDouble("VOLUME"))
//					.unit(rs.getString("UNIT"))
//					.build();
//		};
//
//	}
//	
//	public String getSearchQueryForChart(DashboardRequest dRequest) {
//		String selectAllQuery = "";
//		if(dRequest.getGraphUnitFlag()!= null && dRequest.getGraphUnitFlag().equals("MONTH")) {
//			selectAllQuery = DashboardQuery.getChartForMonth;
//			}
//		else if(dRequest.getGraphUnitFlag()!= null && dRequest.getGraphUnitFlag().equals("YEAR")) {
//		selectAllQuery = DashboardQuery.getChartForYear;
//			}
//		
//		else if(dRequest.getGraphUnitFlag()!= null && dRequest.getGraphUnitFlag().equals("DAILY")) {
//			selectAllQuery = DashboardQuery.getChartForDay;
//				}
//		
//		else if(dRequest.getGraphUnitFlag()!= null && dRequest.getGraphUnitFlag().equals("HOURLY")) {
//			selectAllQuery = DashboardQuery.getChartForHour;
//				}
//		
//		else if(dRequest.getGraphUnitFlag()!= null && dRequest.getGraphUnitFlag().equals("WEEKLY")) {
//			selectAllQuery = DashboardQuery.getChartForWeek;
//				}
//		
//		StringBuffer whereBuffer = new StringBuffer("");
//
//				if (!StringUtils.isBlank(dRequest.getMerchantId())) {
//			whereBuffer.append(" AND MERCHANT_ID = ?");
//		}
//
//		if (!StringUtils.isBlank(dRequest.getCardHolderName())) {
//			whereBuffer.append(" AND CARD_HOLDER_NAME= ?");
//		}
//		
//		if (!StringUtils.isBlank(dRequest.getCardNumberEnc())) {
//			whereBuffer.append(" AND CARD_NUMBER_ENC = ?");
//		}
//
//		if (!StringUtils.isBlank(dRequest.getCardType())) {
//			whereBuffer.append(" AND CARD_TYPE = ?");
//		}
//		
//		if (!StringUtils.isBlank(dRequest.getPaymentScheme())) {
//			whereBuffer.append(" AND PAYMENT_SCHEME = ?");
//		}
//		if (!StringUtils.isBlank(dRequest.getTermId())) {
//			whereBuffer.append(" AND TERM_ID = ?");
//		}
//
//		if (!StringUtils.isBlank(dRequest.getCountry())) {
//			whereBuffer.append(" AND COUNTRY = ?");
//		}
//		
//		if (!StringUtils.isBlank(dRequest.getPaymentMethod())) {
//			whereBuffer.append(" AND PAYMENT_METHOD = ?");
//		}
//		
//		if (!StringUtils.isBlank(dRequest.getInvoicePayment())) {
//			whereBuffer.append(" AND INVOICE_PAYMENT= ?" );
//		}
//		
//		selectAllQuery += whereBuffer;
//		log.info(selectAllQuery);
//		selectAllQuery.replaceAll("%s", AppUtil.getCurrentMerchantIdStr());
//		return selectAllQuery;
//
//	}
//	
//	
//}