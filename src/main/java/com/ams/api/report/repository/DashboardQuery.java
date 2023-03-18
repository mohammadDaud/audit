package com.ams.api.report.repository;

public class DashboardQuery {
	
	 	 public static String getAllRecords = "SELECT * FROM VW_TRANSACTION_DASHBOARD WHERE TRUNC(TRAN_DATE)";
	 	 
	 	 
	 	 public static String getCountAmountWithPaymentStatus = "select 'Payment_status' category, count(id) count, sum(AMOUNT) volume, STATUS from Vw_transaction_dashboard WHERE status in('SUCCESS', 'FAIL') "
	 	 		+ "and TRUNC(TRAN_DATE) %s group by status"
	 	 		+ " UNION ALL "
	 	 		+ "select 'Transaction' category, count(id) count, sum(AMOUNT) volume,'' as TEMP from Vw_transaction_dashboard where TRUNC(TRAN_DATE) %s "
	 	 		+ " UNION ALL "
	 	 		+ "select 'Payment_Method' category, count(id) count, sum(AMOUNT) volume, payment_method from Vw_transaction_dashboard WHERE payment_method in('REFUND', 'PURCHASE') "
	 	 		+ "and TRUNC(TRAN_DATE) %s group by payment_method";
	 	 
	 	 public static String getChartForMonth = "select rno,unit, sum(count) count, sum(volume) volume from ("
	 	 		+ " select  to_date( to_char(tran_date, 'MON-YY'), 'mon-yyyy' ) rno,  to_char(tran_date, 'MON-YY')  unit , count(*) count, sum(amount) volume "
	 	 		+ "FROM VW_TRANSACTION_DASHBOARD "
	 	 		+ "WHERE tran_date between TRUNC ( (ADD_MONTHS (SYSDATE, -14)), 'MM') and sysdate "
	 	 		+ "group by to_char(tran_date, 'MON-YY') "
	 	 		+ "union "
	 	 		+ " select rno,now, 0,0 "
	 	 		+ "from ( select to_date( to_char(ADD_MONTHS (SYSDATE, -level+1),'MON-YY'), 'mon-yyyy') rno, TO_CHAR(ADD_MONTHS (SYSDATE, -level+1) , 'MON-YY') now from dual  connect by level<=15 ) "
	 	 		+ ")"
	 	 		+ "group by rno,unit "
	 	 		+ "order by 1 desc";
	 	 
	 	 public static String getChartForYear = "select unit, sum(count) count, sum(volume) volume from ("
	 	 		+ " select  to_char(tran_date, 'YYYY')  unit , count(*) count, sum(amount) volume "
	 	 		+ "FROM VW_TRANSACTION_DASHBOARD "
	 	 		+ "WHERE tran_date between TRUNC ( (ADD_MONTHS (SYSDATE, - 11*15)), 'MM') and sysdate "
	 	 		+ "group by to_char(tran_date, 'YYYY') "
	 	 		+ "union "
	 	 		+ " select now, 0,0 "
	 	 		+ "from ( select  TO_CHAR(TO_CHAR(SYSDATE , 'YYYY')+1 -level) now from dual  connect by level<=15 ) )"
	 	 		+ "group by unit "
	 	 		+ "order by 1 desc ";
	 	 
	 	 public static String getChartForDay = "select rno,unit, sum(count) count, sum(volume) volume from ( "
	 	 		+ "select trunc(tran_date) rno,  to_char(tran_date, 'dd-MON')  unit , count(*) count, sum(amount) volume "
	 	 		+ "FROM VW_TRANSACTION_DASHBOARD "
	 	 		+ "WHERE tran_date between trunc(sysdate)-15 and sysdate "
	 	 		+ "group by trunc(tran_date), to_char(tran_date, 'dd-MON') "
	 	 		+ "union "
	 	 		+ "select rno,now, 0,0 "
	 	 		+ "from ( select trunc(sysdate)+1 - level rno, to_char(trunc(sysdate)+1 - level  , 'dd-MON') now from dual  connect by level<=15 ) "
	 	 		+ ")"
	 	 		+ "group by rno,unit "
	 	 		+ "order by 1 desc ";
	 	 
	 	 public static String getChartForHour = "SELECT sum(rno) rno,unit, sum(count) count, sum(volume) volume from "
	 	 		+ "(select 0 rno,to_char(tran_date, 'HH24')  unit , count(*) count, sum(amount) volume "
	 	 		+ "FROM VW_TRANSACTION_DASHBOARD WHERE tran_date between sysdate-15/24 and sysdate group by to_char(tran_date, 'HH24') union "
	 	 		+ "select rno,now, 0,0 "
	 	 		+ "from ( select level rno, to_char(sysdate - ((level-1)/24), 'HH24') now from dual  connect by level<=15 )) group by unit order by rno desc";
	 	 
	 	 public static String getChartForWeek = "SELECT unit, sum(count) count , sum(volume) volume from "
	 	 		+ "(select to_char(TRUNC(tran_date),'WW') weekNum, to_char(TRUNC(tran_date),'yyyy') year, TRUNC(tran_date, 'WW') ||' - ' || (TRUNC(tran_date, 'WW') + 7 - 1/86400 ) unit , count(*) count, sum(amount) volume "
	 	 		+ "FROM VW_TRANSACTION_DASHBOARD WHERE trunc(tran_date) between trunc(sysdate)-105 and trunc(sysdate) "
	 	 		+ "group by to_char(TRUNC(tran_date),'WW') ,to_char(TRUNC(tran_date),'yyyy'), TRUNC(tran_date, 'WW') ||' - ' || (TRUNC(tran_date, 'WW') + 7 - 1/86400 ) union "
	 	 		+ "select to_char(TRUNC(today),'WW') weekNum, to_char(TRUNC(today),'yyyy') year, TRUNC(today, 'WW')   ||' - ' || (TRUNC(today, 'WW') + 7 - 1/86400 )  unit,0 count,0 volume "
	 	 		+ " from ( select sysdate -level today from dual  connect by level<=105 )) "
	 	 		+ "group by weekNum, unit, year order by year desc, weekNum desc";
	 	
}
