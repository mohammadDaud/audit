package com.ams.api.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_TYPE")
public class UserType {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_type_generator")
	@SequenceGenerator(name="user_type_generator", sequenceName = "user_type_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;    
}