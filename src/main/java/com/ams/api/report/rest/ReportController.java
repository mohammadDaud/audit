package com.ams.api.report.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.api.admin.entity.UserLoginHistory;
import com.ams.api.report.model.AllUserLoginHistoryResponse;
import com.ams.api.report.model.UserLoginHistoryRequest;
import com.ams.api.report.model.UserLoginHistoryResponse;
import com.ams.api.report.service.ReportService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "maps/report")
@Api(tags = { "Report Controller" })
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("login-history")
	public AllUserLoginHistoryResponse userLoginHistory(@RequestBody @Valid UserLoginHistoryRequest userLoginHistoryRequest) {

		List<UserLoginHistory> userLoginHistory = reportService.getUserLoginHistory(userLoginHistoryRequest);
		return new AllUserLoginHistoryResponse(userLoginHistory.stream().map(UserLoginHistoryResponse::new)
										.collect(Collectors.toList()));
	}
	
//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get a all user list", notes = "The User list")
//	public AllUserResponse getAllUser() {
//		List<UserDTO> allApprovedUser = this.userService.getUserbyHiddenStatus();
//		allApprovedUser.addAll(this.userTempService.getAllTempUser());
//		return new AllUserResponse(allApprovedUser);
//	}
}