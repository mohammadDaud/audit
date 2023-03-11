//package com.maps.security;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
//public class FilterConfiguration {
//	/*TODO convert below secure url into single regex pattern*/
//	private static String [] SECURE_URLS= {"/maps/*/*/*"};
//	
//	@Bean
//	public FilterRegistrationBean<AuthrizationFilter> authFilter(){
//	    FilterRegistrationBean<AuthrizationFilter> registrationBean = new FilterRegistrationBean<>();
//
//	    registrationBean.setFilter(new AuthrizationFilter());
//	    registrationBean.addUrlPatterns(SECURE_URLS);	    
//	    registrationBean.setOrder(1);
//	    return registrationBean;
//	}
//}