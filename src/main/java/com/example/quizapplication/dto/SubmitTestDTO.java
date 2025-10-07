package com.example.quizapplication.dto;

import java.util.List;

public class SubmitTestDTO {
	
	private String testId;
	private String userId;
	private List<QuestionResponse> responses;
	
	
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<QuestionResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<QuestionResponse> responses) {
		this.responses = responses;
	}

}
