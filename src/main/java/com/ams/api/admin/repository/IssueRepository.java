package com.ams.api.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.api.admin.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, String> {

	List<Issue> findAll();

	Optional<Issue> findById(String issueNo);

}