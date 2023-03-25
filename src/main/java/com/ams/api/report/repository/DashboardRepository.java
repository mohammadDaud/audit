package com.ams.api.report.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ams.api.report.model.DashboardRequest;
import com.ams.constants.AppConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
@RequiredArgsConstructor
public class DashboardRepository {

	private final JdbcTemplate jdbcTemplate;
	private HashMap<String, String> map = new HashMap<>();

	public Map<String, BigDecimal> getAllFindingCountByStatus() {
		
		List<Map<String, Object>> mapList1 =  jdbcTemplate.queryForList(AppConstant.QUERY_CONSTANTS.getAllFindingCount);
	    return mapList1.stream().collect(Collectors.toMap(k -> (String) k.get("STATUS"), k -> (BigDecimal) k.get("COUNT")));
}


}
