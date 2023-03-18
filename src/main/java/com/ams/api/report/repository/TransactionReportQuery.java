package com.ams.api.report.repository;

public class TransactionReportQuery {
	
 	public static String getAllRecords = "SELECT * FROM VW_TRANSACTION_REPORT WHERE 1=1 ";
 	public static String getArchiveRecords = "SELECT * FROM VW_ARCH_TRANSACTION_REPORT WHERE 1=1 ";
 	public static String getAllRecordsCount = "SELECT count(*) FROM VW_TRANSACTION_REPORT WHERE 1=1 ";
 	public static String getArchiveRecordsCount = "SELECT count(*) FROM VW_ARCH_TRANSACTION_REPORT WHERE 1=1 ";
}
