//package com.maps.api.transaction.report.repository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import com.ams.Utility.AppUtil;
//import com.ams.exception.ApplicationException;
//import com.maps.api.transaction.report.model.TransactionInquiryRequest;
//import com.maps.api.transaction.report.model.TransactionReportRequest;
//import com.maps.api.transaction.report.model.TransactionReportResponse;
//
//import io.micrometer.core.instrument.util.StringUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import oracle.net.ano.EncryptionService;
//
//@Log4j2
//@Repository
//@RequiredArgsConstructor
//public class TransactionReportRepository {
//
//	private final JdbcTemplate jdbcTemplate;
//	private HashMap<String, String> map = new HashMap<>();
//	private final EncryptionService encryptionService;
//
//	public String getSearchQuery(TransactionReportRequest tReport, String query) {
//		map.put("PAYMENT_ID", "PAYMENT_ID");
//		map.put("RRN", "RRN");
//		map.put("MERCHANT_REF_ID", "MERCHANT_REF_ID");
//		map.put("TRANSACTION_ID", "TRANS_ID");
//		String selectAllQuery = query;
//		StringBuffer whereBuffer = new StringBuffer("");
//
//		if (StringUtils.isBlank(tReport.getFromDate()) || StringUtils.isBlank(tReport.getToDate())) {
//			throw new ApplicationException("From Date & To Date are required");
//		}
//
//		whereBuffer.append(
//				" AND to_date(FROM_DATE,'dd-MON-yy hh24:mi') between to_date( '" + tReport.getFromDate() + "','dd-MON-yy hh24:mi') AND to_date('" + tReport.getToDate() +"','dd-MON-yy hh24:mi')");
//
//
//		if (StringUtils.isNotBlank(tReport.getInstId())) {
//			whereBuffer.append(" AND INST_ID= '" + tReport.getInstId()+ "'");
//		}
//		
//		if (isNotEmptyOrNotAll(tReport.getMerchantId())) {
//			whereBuffer.append(" AND MERCHANT_ID IN ('" + String.join("','", tReport.getMerchantId()) + "')");
//		}
//
//		if (isNotEmptyOrNotAll(tReport.getTermId())) {
//			whereBuffer.append(" AND TERM_ID IN ('" + String.join("','", tReport.getTermId()) + "')");
//		}
//
//		if (StringUtils.isNotBlank(tReport.getAmount())) {
//			whereBuffer.append(" AND AMOUNT= " + tReport.getAmount());
//		}
//		if (StringUtils.isNotBlank(tReport.getCardBin())) {
//			whereBuffer.append(" AND CARD_BIN= " + "'" + tReport.getCardBin() + "'");
//		}
//		if (StringUtils.isNotBlank(tReport.getCardNumberEnc())) {
//			String encryptedCard = this.encrypt(tReport.getCardNumberEnc(), tReport.getInstId());
//			whereBuffer.append(" AND CARD_NUMBER_ENC = '" + encryptedCard + "'");
//		}
//		
//		if (isNotEmptyOrNotAll(tReport.getCurrencyCode())) {
//			whereBuffer.append(" AND CURRENCY_CODE IN ('" + String.join("','", tReport.getCurrencyCode()) + "')");
//		}		
//
//		if (StringUtils.isNotBlank(tReport.getBatchId())) {
//			whereBuffer.append(" AND BATCH_ID =" + "'" + tReport.getBatchId() + "'");
//		}
//
//		String columnName = map.get(tReport.getTranRefType());
//		if (columnName != null && !StringUtils.isBlank(columnName)) {
//			whereBuffer.append(" AND " + columnName + "='" + tReport.getTranRefValue() + "'");
//		}
//
//		if (isNotEmptyOrNotAll(tReport.getPaymentInstrmnt())) {
//			String str = String.join("','", tReport.getPaymentInstrmnt());
//			log.info("======================" + str);
//			whereBuffer.append(" AND PAYMENT_INSTRMNT IN ('" + str + "')");
//		}
//
//		if (isNotEmptyOrNotAll(tReport.getCardType())) {
//			whereBuffer.append(" AND CARD_TYPE IN ('" + String.join("','", tReport.getCardType()) + "')");
//		}
//
//		if (isNotEmptyOrNotAll(tReport.getActionCode())) {
//			whereBuffer.append(" AND ACTION_CODE IN ('" + String.join("','", tReport.getActionCode()) + "')");
//		}
//
//		if (isNotEmptyOrNotAll(tReport.getPmtStatus())) {
//			whereBuffer.append(" AND PMT_STATUS IN ('" + String.join("','", tReport.getPmtStatus()) + "')");
//		}
//
//		if (StringUtils.isNotBlank(tReport.getCheckds()) && !tReport.getCheckds().equals("both")) {
//			whereBuffer.append(" AND THREEDS_FLAG= " +( "3DS".equals(tReport.getCheckds())? 1 : 0) + "");
//		}		
//		if(tReport.getIncludeCountQuery().equals("false"))
//			whereBuffer.append(" ORDER BY TO_DATE desc OFFSET " + tReport.getPageSize() + " rows fetch NEXT "
//					+ tReport.getPageLength() + " rows only");
//		
//		selectAllQuery += whereBuffer;
//		log.info(selectAllQuery);
//		return selectAllQuery;
//
//	}
//
//	public List<TransactionReportResponse> getAllTransactionList(TransactionReportRequest transactionReport) {
//		transactionReport.setIncludeCountQuery("false");
//		String searchQuery = getSearchQuery(transactionReport, TransactionReportQuery.getAllRecords);
//		return jdbcTemplate.query(searchQuery, mapResults());
//	}
//	
//	public Long getAllTransactionListCount(TransactionReportRequest transactionReport) {
//		transactionReport.setIncludeCountQuery("true");
//		String searchQuery = getSearchQuery(transactionReport, TransactionReportQuery.getAllRecordsCount);
//		return jdbcTemplate.queryForObject(searchQuery, Long.class);
//	}
//	
//	public Long getAllArchievedTransactionListCount(TransactionReportRequest transactionReport) {
//		transactionReport.setIncludeCountQuery("true");
//		String searchQuery = getSearchQuery(transactionReport, TransactionReportQuery.getArchiveRecordsCount);
//		return jdbcTemplate.queryForObject(searchQuery, Long.class);
//	}
//	
//	
//	public List<TransactionReportResponse> getArchiveTransactionList(TransactionReportRequest transactionReport) {
//		String searchQuery = getSearchQuery(transactionReport, TransactionReportQuery.getArchiveRecords);
//		return jdbcTemplate.query(searchQuery, mapResults());
//	}
//
//	private RowMapper<TransactionReportResponse> mapResults() {
//
//		return (rs, rowNum) -> {
//			return TransactionReportResponse.builder().actionCode(rs.getString("ACTION_CODE"))
//					.amount(rs.getDouble("AMOUNT")).applePayFlag(rs.getBoolean("APPLEPAY_FLAG"))
//					.areqData(rs.getString("AREQ_DATA")).aresData(rs.getString("ARES_DATA"))
//					.authCode(rs.getString("AUTH_CODE")).batchId(rs.getString("BATCH_ID"))
//					.cardBin(rs.getShort("CARD_BIN")).cardExpiryMonth(rs.getString("CARD_EXPIRY_MONTH"))
//					.cardExpiryYear(rs.getString("CARD_EXPIRY_YEAR")).cardHolderName(rs.getString("CARD_HOLDER_NAME"))
//					.cardNumberMask(rs.getString("CARD_NUMBER_MASK")).cardType(rs.getString("CARD_TYPE"))
//					.creqData(rs.getString("CREQ_DATA")).cresData(rs.getString("CRES_DATA"))
//					.currencyCode(rs.getShort("CURRENCY_CODE")).custEmail(rs.getString("CUST_EMAIL"))
//					.custMobNum(rs.getString("CUST_MOB_NUM")).extConId(rs.getString("EXT_CON_ID"))
//					.fromDate(rs.getString("FROM_DATE"))
//					.instId(rs.getString("INST_ID")).instPId(rs.getLong("INST_PID"))
//					.threedsFlag(rs.getBoolean("THREEDS_FLAG")).ipAddress(rs.getString("IP_ADDRESS"))
//					.merchantId(rs.getString("MERCHANT_ID")).merchantRefId(rs.getString("MERCHANT_REF_ID"))
//					.pareqData(rs.getString("PAREQ_DATA")).paresData(rs.getString("PARES_DATA"))
//					.paymentId(rs.getString("PAYMENT_ID")).paymentInstrmnt(rs.getString("PAYMENT_INSTRMNT"))
//					.pmtStatus(rs.getString("PMT_STATUS")).responseCode(rs.getString("RESPONSE_CODE"))
//					.riskRespCode(rs.getString("RISK_RESP_CODE")).termId(rs.getString("TERM_ID"))
//					.toDate(rs.getString("TO_DATE"))
//					.tranPortalId(rs.getString("TRAN_PORTAL_ID"))
//					.transId(rs.getString("TRANS_ID")).tranType(rs.getString("TRAN_TYPE"))
//					.vreqData(rs.getString("VEREQ_DATA")).vresData(rs.getString("VERES_DATA"))
//					.customField1(rs.getString("CUSTOM_FIELD1")).customField2(rs.getString("CUSTOM_FIELD2"))
//					.customField3(rs.getString("CUSTOM_FIELD3")).customField4(rs.getString("CUSTOM_FIELD4"))
//					.customField5(rs.getString("CUSTOM_FIELD5")).customField6(rs.getString("CUSTOM_FIELD6"))
//					.customField7(rs.getString("CUSTOM_FIELD7")).customField8(rs.getString("CUSTOM_FIELD8"))
//					.customField9(rs.getString("CUSTOM_FIELD9")).customField10(rs.getString("CUSTOM_FIELD10"))
//					.customField11(rs.getString("CUSTOM_FIELD11")).customField12(rs.getString("CUSTOM_FIELD12"))
//					.customField13(rs.getString("CUSTOM_FIELD13")).customField14(rs.getString("CUSTOM_FIELD14"))
//					.customField15(rs.getString("CUSTOM_FIELD15")).customField16(rs.getString("CUSTOM_FIELD16"))
//					.customField17(rs.getString("CUSTOM_FIELD17")).customField18(rs.getString("CUSTOM_FIELD18"))
//					.customField19(rs.getString("CUSTOM_FIELD19")).customField20(rs.getString("CUSTOM_FIELD20"))
//					.currencyName(rs.getString("CURRENCY_NAME")).rrn(rs.getString("RRN"))
//					.build();
//		};
//
//	}
//
//	private Boolean isNotEmptyOrNotAll(List<String> value) {
//		return value != null && !value.isEmpty() && !value.contains("ALL");
//	}
//
//	public List<TransactionReportResponse> getTransactionInquiry(TransactionInquiryRequest inquiryRequest) {
//
//		map.put("PAYMENT_ID", "PAYMENT_ID");
//		map.put("RRN", "RRN");
//		map.put("MERCHANT_REF_ID", "MERCHANT_REF_ID");
//		map.put("TRANSACTION_ID", "TRANS_ID");
//		String selectInquiryQuery = TransactionReportQuery.getAllRecords;
//		StringBuffer whereBuffer = new StringBuffer("");
//		List<Object> param = new ArrayList<>();
//
//		if (!Strings.isBlank(inquiryRequest.getTerminalId())) {
//			whereBuffer.append(" AND TERM_ID = ? ");
//			param.add(inquiryRequest.getTerminalId());
//		}
//
//		if (inquiryRequest.getTranRefType() != null && !StringUtils.isBlank(inquiryRequest.getTranRefType())) {
//
//			String refType = map.get(inquiryRequest.getTranRefType());
//
//			if (refType == null || StringUtils.isBlank(refType)) {
//				throw new ApplicationException("Invalid Reference Type");
//			}
//
//			whereBuffer.append(" AND " + refType + "= ? ");
//			param.add(inquiryRequest.getTranRefValue());
//		}
//		selectInquiryQuery += whereBuffer;
//		log.debug(selectInquiryQuery);
//
//		return jdbcTemplate.query(selectInquiryQuery, param.toArray(), mapResults());
//
//	}
//
//	private String encrypt(String card, String instId) {
//		try {
//			if (AppUtil.isAdmin())
//			return encryptionService.getTextEncryptedWithDEK(card.toCharArray(), instId);
//			return encryptionService.getTextEncryptedWithDEK(card.toCharArray(), AppUtil.getCurrentInstIdStr());
//		}
//		catch (Exception e) {
//			throw new ApplicationException("Error while Encrypting Card Details", e);
//		}
//	}
//}
