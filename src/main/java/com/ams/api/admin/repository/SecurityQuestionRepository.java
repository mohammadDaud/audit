package com.ams.api.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ams.api.admin.entity.SecurityQuestion;

public interface SecurityQuestionRepository extends CrudRepository<SecurityQuestion, Long>{
	
	List<SecurityQuestion> findAll();
	List <SecurityQuestion> findByUserUserId(String userId);
	Optional<SecurityQuestion> findByQuestionAndAnswerAndUserUserId(String question, String answer, String userName);
}
