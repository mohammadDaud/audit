package com.maps.common.rest;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CacheController {
	private final CacheManager cacheManager;
	
	@GetMapping("external/cache/clear/{cacheName}")
	public String clearCache(@PathVariable String cacheName) throws Exception {
		 log.info("Clearing cache for {}", cacheName);
		 cacheManager.getCache(cacheName).clear();
		 return "Cache "+cacheName+" cleared successfully";
	}
	
	@GetMapping("external/cache")
	public List<String> getCacheNames() throws Exception {
		 log.info("Get All cache");
		 return cacheManager.getCacheNames().stream().collect(Collectors.toList());
	}
}
