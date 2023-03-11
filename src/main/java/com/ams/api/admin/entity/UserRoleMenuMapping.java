package com.ams.api.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.ams.api.admin.model.MenuToRoleMappingRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLE_PRIVILEGE")
public class UserRoleMenuMapping {

    public UserRoleMenuMapping(MenuToRoleMappingRequest menuToRoleMappingRequest) {
		BeanUtils.copyProperties(menuToRoleMappingRequest, this);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_privilege_generator")
	@SequenceGenerator(name="role_privilege_generator", sequenceName = "role_privilege_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String action;

    @ManyToOne
	@JoinColumn
    private UserRole userRole;
        
    @ManyToOne
	@JoinColumn
    private Menu menu;   
}
