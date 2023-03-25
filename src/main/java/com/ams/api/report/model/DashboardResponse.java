package com.ams.api.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
	private Map<String, BigDecimal> allFindingMap = new HashMap<>();
	private List<String> allFindingStatus = new ArrayList<>();
	private List<BigDecimal> allFindingStatusCount = new ArrayList<>();
	
}
