package com.ams.api.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCountVolumeResponse{
	private String category;
	private Double volume;
	private String status;
	private String count;
}
