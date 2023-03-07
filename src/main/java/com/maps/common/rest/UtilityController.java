package com.maps.common.rest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "external")
@RequiredArgsConstructor
public class UtilityController {
	private final DataSource dataSource;

	@GetMapping("connection")
	public Map getImage(HttpServletRequest request) throws SQLException {
		Map<String, String> map = new HashMap<>();
		map.put("SCHEMA", dataSource.getConnection().getSchema());
		map.put("URL", dataSource.getConnection().getMetaData().getURL());
		map.put("UerName", dataSource.getConnection().getMetaData().getUserName());
			return map;
	}
}