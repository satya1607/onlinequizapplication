package com.example.quizapplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.TestResultDTO;

@Document(collection="TestResult")
public class TestResult {
	
    @Id
	private Long id;
	private int totalQuestions;
	private int correctAnswers;
	private double percentage;
	
	@DBRef
	private Test test;
	
	@DBRef
	private User user;
	
	public TestResultDTO getDto() {
		TestResultDTO dto=new TestResultDTO();
		dto.setId(id);
		dto.setTotalQuestions(totalQuestions);
		dto.setCorrectAnswers(correctAnswers);
		dto.setPercentage(percentage);
		dto.setTestName(test.getTitle());
		dto.setUserName(user.getName());
		
		return dto;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public int getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
