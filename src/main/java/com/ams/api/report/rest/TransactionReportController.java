//package com.maps.api.report.rest;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.maps.api.report.service.TransactionReportService;
//import com.maps.api.transaction.model.TransactionDTO;
//import com.maps.api.transaction.model.TransactionSearch;
//import com.maps.api.transaction.report.model.TransactionInquiryRequest;
//import com.maps.api.transaction.report.model.TransactionReportRequest;
//import com.maps.api.transaction.report.model.TransactionReportResponse;
//import com.maps.api.transaction.report.service.TransactionReportListService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@Api(tags = { "Transaction Report Controller" })
//@RequiredArgsConstructor
//public class TransactionReportController {
//
//	private final TransactionReportService transactionReportService;
//	private final TransactionReportListService transactionReportListService;
//
//	@GetMapping("maps/report/transaction/search-transaction")
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiOperation(value = "Search Transaction", notes = "Search Transaction")
//	public List<TransactionDTO> listAllTransaction(@RequestBody TransactionSearch transactionSearch){
//		return this.transactionReportService.searchTransaction(transactionSearch);
//	}
//	
//	@PostMapping("maps/report/transaction/list")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get all list", notes = "The Transaction list")
//	public List<TransactionReportResponse> getTransactionReport(@RequestBody @Valid TransactionReportRequest transactionReportRequest){
//		return this.transactionReportListService.getAllTransactionRecords(transactionReportRequest);
//	}
//	
//	@PostMapping("maps/report/transaction/count")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get all list", notes = "The Transaction list")
//	public Long getTransactionReportCount(@RequestBody @Valid TransactionReportRequest transactionReportRequest){
//		return this.transactionReportListService.getAllTransactionCount(transactionReportRequest);
//	}
//	
//	@PostMapping("external/inquiry")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get all list", notes = "The Transaction Inquiry")
//	public List<TransactionReportResponse> getTransactionInquiry(@RequestBody @Valid TransactionInquiryRequest transactionInquiryRequest){
//		return this.transactionReportListService.getAllTransactionInquiry(transactionInquiryRequest);
//	}
//}