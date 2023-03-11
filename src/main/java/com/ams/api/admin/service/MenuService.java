package com.ams.api.admin.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ams.api.admin.entity.Menu;
import com.ams.api.admin.model.MenuCreationRequest;
import com.ams.api.admin.model.MenuDTO;
import com.ams.api.admin.model.MenuGroup;
import com.ams.api.admin.model.MenuUpdateRequest;
import com.ams.api.admin.repository.MenuRepository;
import com.ams.common.service.MessageSourceService;
import com.ams.exception.ApplicationException;
import com.ams.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MenuService {

	private final MenuRepository menuRepository;
	private final String PORTAL_ADM = "A";
	private final String PORTAL_OPS = "O";
	private final String PORTAL_MERCH = "M";
	private final static String MENU = "all-menu";
	private final MessageSourceService messageSource;

	
	@CacheEvict(cacheNames = MENU, allEntries = true)
	public Menu createMenu(MenuCreationRequest menuCreationRequest) {
		log.info("===========Menu Creation ===========");
		Menu menu = new Menu(menuCreationRequest);
		if (menuCreationRequest.getParent() != 0)
			menu.setParent(getMenu(menuCreationRequest.getParent()));
		if(isNameExist(menuCreationRequest.getName())) {
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ALREADY_EXIST,"Menu Name"));
		}

		return save(menu);
	}

	@CacheEvict(cacheNames = MENU ,allEntries = true)
	public Menu updateMenu(MenuUpdateRequest menuUpdateRequest) {
		log.info("===========Menu Update ===========");
		
		Menu menu = menuRepository.findById(menuUpdateRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(MessageSourceService.NOT_FOUND,"Menu")));
		BeanUtils.copyProperties(menuUpdateRequest, menu, "id");
		MenuEditCheck(menuUpdateRequest);
		MenuKeyEditCheck(menuUpdateRequest);
		if (menuUpdateRequest.getParent() != 0)
			menu.setParent(getMenu(menuUpdateRequest.getParent()));
		else
			menu.setParent(null);
		
		return this.save(menu);
	}
	private void MenuKeyEditCheck(MenuUpdateRequest menuUpdateRequest) {
		Optional<Menu> menu = this.menuRepository.findByKey(menuUpdateRequest.getKey());
		if( menu.isPresent() && menu.get().getId() != menuUpdateRequest.getId())
		{
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ALREADY_EXIST,"Menu Key"));
			
		}
	}
	
	private void MenuEditCheck(MenuUpdateRequest menuUpdateRequest) {
		Optional<Menu> menu = this.menuRepository.findByName(menuUpdateRequest.getName());
		if( menu.isPresent() && menu.get().getId() != menuUpdateRequest.getId())
		{
			throw new ApplicationException(messageSource.getMessage(MessageSourceService.ALREADY_EXIST,"Menu Name"));
			
		}
	}
	
	private Menu save(Menu menu) {
		return menuRepository.save(menu);
	}

	public Menu getMenu(long id) throws ResourceNotFoundException {
		return menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(MessageSourceService.NOT_FOUND,"Menu")));
	}

	public List<MenuDTO> getAllMenuByPortal(String portal) {
			return menuRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(MenuDTO::new)
					.collect(Collectors.toList());
		}
	@Cacheable(cacheNames = MENU)
	public List<MenuDTO> getAllMenu() {
		log.info("=====Repository call ========");
		return menuRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(MenuDTO::new)
					.collect(Collectors.toList());
	}

	public List<MenuGroup> getMenuGroupByParent(String portal) {
			List<MenuDTO> allMenu = this.getAllMenuByPortal(portal);
		return groupByParent(allMenu);
	}
	@Cacheable(cacheNames = "user-menu", key = "#userId")
	public List<MenuGroup> getMenuByUserGroupByParent(String userId) {
		List<MenuDTO> allMenu = this.getMenuByUser(userId);
		return groupByParent(allMenu);
	}

	private List<MenuGroup> groupByParent(List<MenuDTO> allMenu) {
		List<MenuGroup> menuGroupList = new ArrayList<>();
		allMenu.sort(Comparator.comparing(MenuDTO::getParent));

		allMenu.stream().forEach((menuDTO) -> {
			if (menuDTO.getParent() == 0) {
				MenuGroup menuGroup = new MenuGroup(menuDTO);
				menuGroupList.add(menuGroup);
			}

			Optional<MenuGroup> findFirst = menuGroupList.stream().filter(m -> m.getId() == menuDTO.getParent())
					.findFirst();

			if (findFirst.isPresent()) {
				MenuGroup menuGroup = findFirst.get();
				menuGroup.getSubMenu().add(menuDTO);
			}
		});

		return  menuGroupList.stream().filter(menuGroup -> !menuGroup.getSubMenu().isEmpty()).collect(Collectors.toList());
	}

	private List<MenuDTO> getMenuByUser(String userId) {
		return menuRepository.findDistinctBylinkedRolesUserRoleUsersUserIdOrParentOrderByDisplayOrder(userId, null).stream().map(MenuDTO::new)
				.collect(Collectors.toList());

	}

	public List<MenuDTO> getAllParentMenu() {
		return menuRepository.findByParentOrderById(null).stream().map(MenuDTO::new).collect(Collectors.toList());

	}
	@CacheEvict(cacheNames = MENU, allEntries = true)
	//@LogDeleteAudit(module = Module.MENU)
	public void deleteMenu(long id) {
		this.menuRepository.deleteById(id);
	}
	
	public Optional<Menu> getMenuByKey(String menuKey) throws ResourceNotFoundException {
		return menuRepository.findByKey(menuKey);
	}
	private boolean isNameExist(String name) {
		return this.menuRepository.findByName(name).isPresent();
	}
	
}