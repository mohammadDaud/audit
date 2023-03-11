package com.ams.api.admin.entity;

import java.time.LocalDateTime;

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
@Table(name = "VW_MRCH_TRANSACTION")
public class VwMrchTransaction{
	@Id
	private long id;
	
	private LocalDateTime paymentInitTime;
	private long amount;
	private String pmtStatus;
	private String merchantId;

}
