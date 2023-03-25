package com.ams.api.report.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ams.api.report.model.DashboardResponse;
import com.ams.api.report.repository.DashboardRepository;
import com.ams.common.service.MessageSourceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final MessageSourceService messageSource;
	
	private final DashboardRepository dashboardRepository;
//	
//	public List<DashboardResponse> getAllTransactionRecords(DashboardRequest dashboardRequest) {
//		dashboardRequest.setCardNumberEnc(encryptText(dashboardRequest.getCardNumberEnc()));
//		return transactionepository.getAllTransactionList(dashboardRequest);
//	}

	public DashboardResponse getAllFindingCountByStatus() {
		
		DashboardResponse dashboardResponse = new DashboardResponse();
		Map<String, BigDecimal> allFindingCountMap = dashboardRepository.getAllFindingCountByStatus();
		dashboardResponse.setAllFindingStatus(new ArrayList<>(allFindingCountMap.keySet()));
		dashboardResponse.setAllFindingStatusCount(new ArrayList<>(allFindingCountMap.values()));
		
		allFindingCountMap.put("TOTAL", allFindingCountMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
		dashboardResponse.setAllFindingMap(allFindingCountMap);
		
		return dashboardResponse;
	}
	
}