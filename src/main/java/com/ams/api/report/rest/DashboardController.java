package com.ams.api.report.rest;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.api.report.model.DashboardRequest;
import com.ams.api.report.model.DashboardResponse;
import com.ams.api.report.service.DashboardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "dashboard")
@Api(tags = { "Dashboard Controller" })
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService dashboardService;
	

//	@GetMapping("/search-transaction")
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiOperation(value = "Search Transaction", notes = "Search Transaction")
//	public List<TransactionDTO> listAllTransaction(@RequestBody TransactionSearch transactionSearch){
//		return this.transactionReportService.searchTransaction(transactionSearch);
//	}
	
//	@PostMapping("/list")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get all list", notes = "The Transaction list")
//	public List<DashboardResponse> getTransactionReport(@RequestBody DashboardRequest dashboardRequest){
//		return this.transactionReportService.getAllTransactionRecords(dashboardRequest);
//	}
	
	@GetMapping("/getData")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get count volume", notes = "The Transaction count volume")
	public DashboardResponse getAllFindingCountByStatus(){
		return this.dashboardService.getAllFindingCountByStatus();
	}
	
}