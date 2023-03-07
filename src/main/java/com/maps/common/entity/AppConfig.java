package com.maps.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	public AppConfig(String key,String value){
		this.key = key;
		this.value = value;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	@SequenceGenerator(name="idGenerator", sequenceName="APP_CONFIG_SEQ", allocationSize = 1)
	private long id;

	@Column(nullable = false)
	private String key;

	@Column(nullable = false)
	private String value;
	
	@Column(nullable = false)
	private String category;
}