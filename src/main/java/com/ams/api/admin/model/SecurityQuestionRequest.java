package com.ams.api.admin.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SecurityQuestionRequest {
		
		private List<QuestionAnswer> questionAnswerList;
}