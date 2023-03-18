package com.ams.api.admin.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ams.Utility.AppUtil;
import com.ams.api.admin.model.AllMenuGroupResponse;
import com.ams.api.admin.model.AllMenuResponse;
import com.ams.api.admin.model.MenuCreationRequest;
import com.ams.api.admin.model.MenuDTO;
import com.ams.api.admin.model.MenuUpdateRequest;
import com.ams.api.admin.service.MenuService;
import com.ams.exception.ForbiddenException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "maps/admin/menu")
@Api(tags = { "Menu" })
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a menu.", notes = "Returns the newly created menu")
	public MenuDTO createMenu(@RequestBody @Valid MenuCreationRequest menuCreationRequest) {

		return new MenuDTO(this.menuService.createMenu(menuCreationRequest));
	}

	@PutMapping("update")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Edit a user role.", notes = "Returns the edited user role")
	public MenuDTO updateMenu(@RequestBody MenuUpdateRequest menuUpdateRequest) {
		return new MenuDTO(this.menuService.updateMenu(menuUpdateRequest));
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all menu list", notes = "The Menu list")
	public AllMenuResponse getAllMenu() {
		return new AllMenuResponse(this.menuService.getAllMenu());
	}

	@GetMapping("/parent")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all menu list", notes = "The Menu list")
	public AllMenuResponse getAllParentMenu() {
		return new AllMenuResponse(this.menuService.getAllParentMenu());
	}

	@GetMapping(value = "/{key}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a single menu.", notes = "You have to provide a valid menu ID.")
	public MenuDTO getMenu(@ApiParam(value = "The ID of the User.", required = true) @PathVariable("key") String key)
			throws Exception {

		return new MenuDTO(this.menuService.getMenu(key));
	}

	@GetMapping("/get-menu-group-by-parent/{flag}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all menu list group by parent", notes = "The Menu list")
	public AllMenuGroupResponse getMenuGroupByParent(@PathVariable String flag) {
		return new AllMenuGroupResponse(this.menuService.getMenuGroupByParent(flag));
	}
	

	@GetMapping("/get-menu-by-user/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a all menu list group by parent", notes = "The Menu list")
	public AllMenuGroupResponse getMenuByRole(@PathVariable String userId) throws Exception {
		validateUser(userId);
		return new AllMenuGroupResponse(this.menuService.getMenuByUserGroupByParent(userId));
	}

	private void validateUser(String userId) {
		String loginUserId= AppUtil.getCurrentUser();
		if(loginUserId == null || !loginUserId.equals(userId)) {
			throw new ForbiddenException("Access Denied"); 
		}
	}

	@DeleteMapping("delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete a menu ", notes = "The Delete Menu")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.menuService.deleteMenu(id);
		return ResponseEntity.ok().body(true);
	}
}