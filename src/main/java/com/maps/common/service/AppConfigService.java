//package com.maps.common.service;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.maps.common.entity.AppConfig;
//import com.maps.common.repository.AppConfigRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class AppConfigService {
//	private final AppConfigRepository appConfigRepository;
//	
//	public AppConfig addAppConfig(String key,String value) {
//		AppConfig appConfig = appConfigRepository.findByKey(key)
//										.orElseGet(() -> new AppConfig(key, value));
//		
//		appConfig.setValue(value);
//		return appConfigRepository.save(appConfig);
//	}
//	
//	public List<AppConfig> getAllConfig() {
//		return appConfigRepository.findAll();		
//	}
//}