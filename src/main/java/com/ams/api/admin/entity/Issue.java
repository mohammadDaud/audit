package com.ams.api.admin.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.ams.Utility.AppUtil;
import com.ams.api.admin.model.IssueCreationRequest;
import com.ams.model.FindingStatus;

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

	private String actionPlan;

	private LocalDate actionDate;

	@Enumerated(EnumType.STRING)
	private FindingStatus status;

	private Integer noOfExtension;
	private String followUpResponse;

	private String createdBy;
	
	private String evidenceName;

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
