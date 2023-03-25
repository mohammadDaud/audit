package com.ams.api.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class GraphUnitTypeResponse {
	private String unit;
	private int count;
	private Double volume;
	
	public GraphUnitTypeResponse(
			String unit ){
		this.unit = unit;
		this.volume = 0.0;
		
	}
	
	@Override
	public boolean equals(Object obj) {
			return ((GraphUnitTypeResponse)obj).getUnit().equals(this.getUnit());
	}
}
