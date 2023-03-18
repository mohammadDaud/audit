package com.ams.api.admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.ams.api.admin.model.MenuCreationRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MENU")
public class Menu {

	public Menu(MenuCreationRequest menuCreationrequest) {
		BeanUtils.copyProperties(menuCreationrequest, this);
	}

	
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	List<UserRoleMenuMapping> linkedRoles;

	@Id
	private String key;

	@Column(nullable = false)
	private String name;

	private String description;

	private String action;
	
	private Boolean isAuditable;
	
	@Column(nullable = false)
	private String url;

	@ManyToOne()
	@JoinColumn(name = "PARENT")
	private Menu parent;

	private String subParent;

	@Column(nullable = false)
	private Short displayOrder;

	@Column(nullable = false)
	private String status;
	
	private String imageUrl;
	
}
