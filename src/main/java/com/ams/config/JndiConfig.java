package com.ams.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import liquibase.integration.spring.SpringLiquibase;
@Profile("!dev-prod & dev-test")
@Configuration
public class JndiConfig {

	@Value("${spring.datasource.jndi-name}")
	private String primaryJndiName;

	private JndiDataSourceLookup lookup = new JndiDataSourceLookup();
	
	
	@Bean(destroyMethod = "") 
	public DataSource primaryDs() {
		return lookup.getDataSource(primaryJndiName);
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.liquibase")
	public LiquibaseProperties primaryLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public SpringLiquibase primaryLiquibase() {
		return springLiquibase(primaryDs(), primaryLiquibaseProperties());
	}

	private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(properties.getChangeLog());
		liquibase.setContexts(properties.getContexts());
		liquibase.setDefaultSchema(properties.getDefaultSchema());
		liquibase.setDropFirst(properties.isDropFirst());
		liquibase.setShouldRun(properties.isEnabled());
		liquibase.setLabels(properties.getLabels());
		liquibase.setChangeLogParameters(properties.getParameters());
		liquibase.setRollbackFile(properties.getRollbackFile());
		return liquibase;
	}

}
