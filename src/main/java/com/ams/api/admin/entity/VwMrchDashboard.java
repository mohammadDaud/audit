package com.ams.api.admin.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "VW_MRCH_DASH_BOARD")
public class VwMrchDashboard{
	@Id
	private long id;
	
	private long count;
	private double amount;
	private String pmtStatus;
	private String merchantId; 

}
