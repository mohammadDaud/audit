package com.ams.api.report.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginHistoryRequest {
	
	private String UserId;
	
	private String UserName;

	@NotNull(message = "Please provide From Date")
	private LocalDate fromDate;
	
	@NotNull(message = "Please provide To Date")
	private LocalDate toDate;
	
	private String instId;
    
    private String merchId;
}