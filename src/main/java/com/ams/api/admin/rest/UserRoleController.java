package com.ams.api.admin.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.api.admin.model.AllUserRoleResponse;
import com.ams.api.admin.model.MenuToRoleMappingRequest;
import com.ams.api.admin.model.UserRoleCreationRequest;
import com.ams.api.admin.model.UserRoleDTO;
import com.ams.api.admin.model.UserRoleUpdateRequest;
import com.ams.api.admin.service.MenuService;
import com.ams.api.admin.service.UserRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "admin/user-role")
@Api(tags = { "User-Role" })
@RequiredArgsConstructor
@Log4j2
public class UserRoleController {

	private final UserRoleService userRoleService;
	private final MenuService menuService;

	@PostMapping("add")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a user role.", notes = "Returns the newly created user role")
	public UserRoleDTO createUserRole(@RequestBody UserRoleCreationRequest userRoleCreationRequest) {
		return new UserRoleDTO(this.userRoleService.createUserRole(userRoleCreationRequest));
	}

	@PutMapping("update")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Edit a user role.", notes = "Returns the edited user role")
	public UserRoleDTO updateUserRole(@RequestBody UserRoleUpdateRequest userRoleUpdateRequest) {
			return new UserRoleDTO(this.userRoleService.updateUserRole(userRoleUpdateRequest));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all user role list", notes = "The user role list")
	public AllUserRoleResponse getAllUserRole() {
		List<UserRoleDTO> allApprovedUserRole = userRoleService.getAllUserRole();
		return new AllUserRoleResponse(allApprovedUserRole);
	}

	@GetMapping(value = "/{roleName}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a single user role.", notes = "You have to provide a valid user role Id.")
	public UserRoleDTO getUserRole(
			@ApiParam(value = "The Id of the User role.", required = true) @PathVariable("roleName") String roleName,
			@RequestParam(name = "status", required = false) String status) throws Exception {

		return new UserRoleDTO(this.userRoleService.getUserRole(roleName));
	}

	@PostMapping("map-menu/update")
	@ResponseStatus(HttpStatus.OK)
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")

	@ApiOperation(value = "Map menu to role", notes = "Map menu to role")
	public void mapMenuToRole(@RequestBody MenuToRoleMappingRequest menuToRoleMappingRequest) {

		this.userRoleService.mapMenuToRole(menuToRoleMappingRequest);
	}

	@DeleteMapping("delete/{roleName}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete a Role ", notes = "The Delete Role")
	public ResponseEntity<Boolean> delete(@PathVariable("roleName") String roleName) {

			this.userRoleService.deleteUserRoleByName(roleName);
		return ResponseEntity.ok().body(true);
	}

	@PostMapping("approveReject/{id}/{status}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all role list", notes = "The role list")
	public ResponseEntity<Boolean> approveRejectRole(
			@RequestParam(value = "comments", required = false) String comments, @PathVariable("id") long id,
			@PathVariable("status") String status) {

		return ResponseEntity.ok().body(true);
	}

}