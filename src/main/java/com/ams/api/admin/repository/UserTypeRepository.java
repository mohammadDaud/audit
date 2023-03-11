package com.ams.api.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ams.api.admin.entity.UserType;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface UserTypeRepository extends CrudRepository<UserType, Long> {

	List<UserType> findAll();
}
