package com.ams.api.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.ams.api.admin.model.QuestionAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PWD_QUESTION")
public class SecurityQuestion {

	public SecurityQuestion(QuestionAnswer questionAnswer) {
		BeanUtils.copyProperties(questionAnswer, this);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pwd_question_generator")
	@SequenceGenerator(name = "pwd_question_generator", sequenceName = "pwd_question_seq", allocationSize = 1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private String question;

	private String answer;
}