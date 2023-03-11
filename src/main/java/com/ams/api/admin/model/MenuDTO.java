package com.ams.api.admin.model;

import org.springframework.beans.BeanUtils;

import com.ams.api.admin.entity.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuDTO {
	
	public MenuDTO(Menu menu) {
			BeanUtils.copyProperties(menu, this);
			if(menu.getParent() != null)
				this.parent = menu.getParent().getId();
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
    
    private short displayOrder;
    
    private String status;
    
    private Boolean mcaEnable;
    
    private String imageUrl;
    
    private boolean adminFlag;
	
	private boolean opsFlag;
	
	private boolean merchFlag;
}
