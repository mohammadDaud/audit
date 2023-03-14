package com.ams.api.admin.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.api.admin.model.IssueCreationRequest;
import com.ams.api.admin.service.IssueService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "admin/issue")
@Api(tags = { "User Controller" })
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a Issue.", notes = "Issue")
	public void createUser(@RequestBody @Valid IssueCreationRequest userCreationRequest) {

		this.issueService.createIssue(userCreationRequest);
	}

//	@PutMapping("/update")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Update a user.", notes = "Returns the newly created menu")
//	public UserDTO updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
//
//		Menu userMenu = menuService.getMenuByKey(MenuKeyEnum.USER.menuKey())
//				.orElseThrow(() -> new ResourceNotFoundException(
//						String.format("No Menu found with key%s", MenuKeyEnum.USER.menuKey())));
//
//		return new UserDTO(this.userService.updateUser(userUpdateRequest));
//	}
//
//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get a all user list", notes = "The User list")
//	public AllUserResponse getAllUser() {
//		List<UserDTO> allApprovedUser = this.userService.getAllUser();
//		return new AllUserResponse(allApprovedUser);
//	}
//
//	@GetMapping("/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get a all user list", notes = "The User list")
//	public UserDTO getUserById(@PathVariable("id") long id,
//			@RequestParam(name = "status", required = false) String status) {
//
//		return new UserDTO(this.userService.getUser(id));
//	}
//
//	@DeleteMapping("/delete/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Delete a user ", notes = "The User list")
//	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
//
//		Menu userMenu = menuService.getMenuByKey(MenuKeyEnum.USER.menuKey())
//				.orElseThrow(() -> new ResourceNotFoundException(
//						String.format("No Menu found with key%s", MenuKeyEnum.USER.menuKey())));
//
//		this.userService.deleteUser(id);
//		return ResponseEntity.ok().body(true);
//	}
//
//	@PostMapping("/change-password")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Create a menu.", notes = "Returns the newly created menu")
//	public ResponseEntity<Boolean> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
//
//		userService.changePassword(changePasswordRequest);
//		return ResponseEntity.ok().body(true);
//	}
//
//	@PostMapping("/approveReject/{id}/{status}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get a all question list", notes = "The QuestionAnswer list")
//	public ResponseEntity<Boolean> approveRejectUser(
//			@RequestParam(value = "comments", required = false) String comments, @PathVariable("id") long id,
//			@PathVariable("status") String status) {
//
//		return ResponseEntity.ok().body(true);
//	}
//
//	@GetMapping("/all-users")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Get a all users list", notes = "The operation and admin users list")
//	public AllUserResponse getAllAdminAndOperationUsers() {
//		return new AllUserResponse(this.userService.getAllUser());
//	}
//
//	@PostMapping("/send-email-link")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(value = "Send email link", notes = "Resend password")
//	public void sendLinks(@RequestBody PasswordResendRequest passwordResendRequest) {
//
//		this.userService.sendEmailLinks(passwordResendRequest, false);
//	}

}
