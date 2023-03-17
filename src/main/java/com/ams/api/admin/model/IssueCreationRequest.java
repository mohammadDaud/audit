package com.ams.api.admin.model;

import com.ams.model.FindingStatus;

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

	//dd-mm-yyyy
	private String actionDate;
	
	private String evidenceName;

	private FindingStatus status;

	private Integer noOfExtension;
	private String followUpResponse;

}