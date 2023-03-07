package com.maps.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maps.common.entity.AppConfig;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

	List<AppConfig> findAll();
	
	Optional<AppConfig> findByKey(String key);
}