//package com.ams.api.report.rest;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.maps.api.transaction.report.model.DashboardCountVolumeResponse;
//import com.maps.api.transaction.report.model.DashboardRequest;
//import com.maps.api.transaction.report.model.DashboardResponse;
//import com.maps.api.transaction.report.model.GraphUnitTypeResponse;
//import com.maps.api.transaction.report.service.DashboardService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping(value = "dashboard")
//@Api(tags = { "Dashboard Controller" })
//@RequiredArgsConstructor
//public class DashboardController {
//
//	//private final DashboardService transactionReportService;
//	
//
////	@GetMapping("/search-transaction")
////	@ResponseStatus(HttpStatus.CREATED)
////	@ApiOperation(value = "Search Transaction", notes = "Search Transaction")
////	public List<TransactionDTO> listAllTransaction(@RequestBody TransactionSearch transactionSearch){
////		return this.transactionReportService.searchTransaction(transactionSearch);
////	}
//	
////	@PostMapping("/list")
////	@ResponseStatus(HttpStatus.OK)
////	@ApiOperation(value = "Get all list", notes = "The Transaction list")
////	public List<DashboardResponse> getTransactionReport(@RequestBody DashboardRequest dashboardRequest){
////		return this.transactionReportService.getAllTransactionRecords(dashboardRequest);
////	}
//	
//	@PostMapping("/count")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get count volume", notes = "The Transaction count volume")
//	public List<DashboardCountVolumeResponse> getTransactionCount(@RequestBody DashboardRequest dashboardRequest){
//		return this.transactionReportService.getAllTransactionCount(dashboardRequest);
//	}
//	
//	@PostMapping("/chart")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get count volume", notes = "The Transaction count volume")
//	public List<GraphUnitTypeResponse> getChartTransactionCount(@RequestBody DashboardRequest dashboardRequest){
//		return this.transactionReportService.getChartCountAndAmount(dashboardRequest);
//	}
//}