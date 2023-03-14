package com.ams.api.admin.model;

import java.util.Date;

import com.ams.api.admin.entity.UserRole;
import com.ams.model.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class IssueCreationRequest {

	private String issueNo;

	private String issueDescription;

	private String riskRating;

	private String department;

	private Integer auditYear;

	private String responsiblePerson;

	private String actionPlan;

	private String actionDate;

	private Status status;

	private Integer noOfExtension;
	private String followUpResponse;

}