package com.ams.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class AuditTrailLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_trail_log_generator")
	@SequenceGenerator(name = "audit_trail_log_generator", sequenceName = "audit_trail_log_seq", allocationSize = 1)
	private long id;

	@Column(nullable = false)
	private String moduleName;

	@Column(nullable = false)
	private String action;

	@Column(nullable = false)
	private String entityId;

	@Lob
	private String oldValues;

	@Lob
	private String newValues;

	@Column(nullable = false)
	private String createdBy;

	@Column(nullable = false)
	private LocalDateTime createdOn;

	private String loggedIp;
}