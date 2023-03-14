package com.ams.api.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ams.Utility.AppUtil;
import com.ams.api.admin.model.IssueCreationRequest;
import com.ams.api.admin.model.UserCreationRequest;
import com.ams.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ISSUES")
public class Issue extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public Issue(IssueCreationRequest issueCreationRequest) {
		BeanUtils.copyProperties(issueCreationRequest, this);
	}

	@Id
	private String issueNo;

	private String issueDescription;

	private String riskRating;

	private String department;

	private Integer auditYear;

	
	private String responsiblePerson;

	private Boolean actionPlan;

	private Date actionDate;

	@Enumerated(EnumType.STRING)
	private Status status;

	private Integer noOfExtension;
	private String followUpResponse;

	private String createdBy;

	private LocalDateTime createdOn;

	private String modifiedBy;

	private LocalDateTime modifiedOn;
	@PrePersist
	public void createdAt() {
		this.createdOn = LocalDateTime.now();
		if(AppUtil.isUserLoggedIn())
			createdBy = getCurrentUser();
	}

	@PreUpdate
	public void updatedAt() {
		this.modifiedOn = LocalDateTime.now();
		this.modifiedBy = getCurrentUser();
	}
}
