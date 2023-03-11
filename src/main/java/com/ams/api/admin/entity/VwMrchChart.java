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
@Table(name = "VW_MRCH_CHART_DATA")
public class VwMrchChart{
	@Id
	private long id;
	
	private long count;
	private long amount;
	private String month;
	private String merchantId;

}
