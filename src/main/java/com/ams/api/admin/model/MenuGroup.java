package com.ams.api.admin.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuGroup {
	
	public MenuGroup(MenuDTO menuDTO) {
		BeanUtils.copyProperties(menuDTO, this);
	}
	 
	private long id;
	
	private String Key;

    private String name;

    private String description;
    
    private String type;
    
    private String action;
    
    private String url;
    
    private long parent;
    
    private String subParent;
    
    private String displayOrder;
    
    private String status;
    
    private String imageUrl;
    
    private List<MenuDTO> subMenu = new ArrayList<>();
}