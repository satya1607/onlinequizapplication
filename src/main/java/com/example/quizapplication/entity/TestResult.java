package com.example.quizapplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.TestResultDTO;

@Document(collection="TestResult")
public class TestResult {
	
    @Id
	private String id;
	private int totalQuestions;
	private int correctAnswers;
	private double percentage;
	
	private String testName;
    private String userName;
    private int testSequenceNumber;
	
	public int getTestSequenceNumber() {
		return testSequenceNumber;
	}

	public void setTestSequenceNumber(int testSequenceNumber) {
		this.testSequenceNumber = testSequenceNumber;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@DBRef
	private TestPOJO testPOJO;
	
	@DBRef
	private User user;
	
	public TestResultDTO getDto() {
		TestResultDTO dto=new TestResultDTO();
		dto.setId(id);
		dto.setTotalQuestions(totalQuestions);
		dto.setCorrectAnswers(correctAnswers);
		dto.setPercentage(percentage);
		dto.setTestName(testPOJO.getTitle());
		dto.setUserName(user.getName());
		
		return dto;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	public TestPOJO getTestPOJO() {
		return testPOJO;
	}
	public void setTestPOJO(TestPOJO testPOJO) {
		this.testPOJO = testPOJO;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
