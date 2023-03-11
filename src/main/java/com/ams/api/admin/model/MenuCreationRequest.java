package com.ams.api.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuCreationRequest {

    private String key;

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
    
    private boolean adminFlag;
	
	private boolean opsFlag;
	
	private boolean merchFlag;
}