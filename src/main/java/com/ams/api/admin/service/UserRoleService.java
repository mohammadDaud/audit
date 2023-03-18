package com.ams.api.admin.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ams.api.admin.entity.UserRole;
import com.ams.api.admin.entity.UserRoleMenuMapping;
import com.ams.api.admin.model.MenuToRoleMappingRequest;
import com.ams.api.admin.model.UserRoleCreationRequest;
import com.ams.api.admin.model.UserRoleDTO;
import com.ams.api.admin.model.UserRoleUpdateRequest;
import com.ams.api.admin.repository.UserRoleRepository;
import com.ams.common.service.MessageSourceService;
import com.ams.exception.ApplicationException;
import com.ams.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserRoleService {

	private final UserRoleRepository userRoleRepository;

	private final MenuService menuService;
	private final MessageSourceService messageSource;
	
	public UserRole createUserRole(UserRoleCreationRequest userRoleCreationRequest) {
		log.info("===========User Role Creation ===========");
		UserRole userRole = new UserRole(userRoleCreationRequest);
		if(isRoleNameExist(userRoleCreationRequest.getRoleName())) {
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ALREADY_EXIST,"User Role Name"));
		}

		return userRoleRepository.save(userRole);
	}

	public UserRole updateUserRole(UserRoleUpdateRequest userRoleUpdateRequest) {
		log.info("===========User Role Update ===========");
		UserRole userRole = this.getUserRole(userRoleUpdateRequest.getRoleName());
		UserRoleEditCheck(userRoleUpdateRequest);
		userRole.setRoleName(userRoleUpdateRequest.getRoleName());
		userRole.setDescription(userRoleUpdateRequest.getDescription());

		return userRoleRepository.save(userRole);
	}
	private void UserRoleEditCheck(UserRoleUpdateRequest userRoleUpdateRequest) {
		Optional<UserRole> role = this.userRoleRepository.findByRoleName(userRoleUpdateRequest.getRoleName());
		if( role.isPresent() && role.get().getRoleName() != userRoleUpdateRequest.getRoleName())
		{
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ALREADY_EXIST,"User Role Name"));
			
		}
	}
	public UserRole getUserRole(String id) throws ResourceNotFoundException {
		return userRoleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(MessageSourceService.NOT_FOUND,"User Role")));
	}

	public List<UserRoleDTO> getAllUserRole() {
		return userRoleRepository.findAll(Sort.by(Sort.Direction.ASC, "roleName")).stream().map(UserRoleDTO::new)
				.collect(Collectors.toList());
	}

	public void mapMenuToRole(MenuToRoleMappingRequest menuToRoleMappingRequest) {

		log.info("===========User Role Menu Mapping ===========");
		Optional<UserRole> userRoleOpt = userRoleRepository.findByRoleName(menuToRoleMappingRequest.getUserRoleName());
		UserRole userRole = userRoleOpt.orElseThrow(() -> new ResourceNotFoundException
				(messageSource.getMessage(MessageSourceService.NOT_FOUND,"User Role")));
		userRole.getAssignedMenus().clear();
		Arrays.stream(menuToRoleMappingRequest.getMenuItem()).forEach((menuItem) -> {
			UserRoleMenuMapping userRoleMenuMapping = new UserRoleMenuMapping();
			userRoleMenuMapping.setMenu(menuService.getMenu(menuItem.getMenuKey()));
			userRoleMenuMapping.setUserRole(userRole);
			userRoleMenuMapping.setAction(menuItem.getAction()); // TODO temp untill UI ready
			userRole.getAssignedMenus().add(userRoleMenuMapping);
		});

		userRoleRepository.save(userRole);
	}
	
	@Transactional
	public void deleteUserRoleByName(String id) {
		this.userRoleRepository.deleteByRoleName(id);
	}
	

	public UserRole save(UserRole userRole) {
		return userRoleRepository.save(userRole);
	}

	
	public Optional<UserRole> getUserRoleByRoleName(String roleName) {
		return userRoleRepository.findByRoleName(roleName);
	}
	
	private boolean isRoleNameExist(String name) {
		
		return this.userRoleRepository.findByRoleName(name).isPresent();
	}
	
	
}