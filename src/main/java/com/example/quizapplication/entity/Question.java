package com.example.quizapplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.QuestionDTO;

@Document(collection="Question")
public class Question {
    @Id
	private String id;
    @Indexed(unique = true) 
    private String testid;
	private String questionText;
	private String optionA;
	private String optionB;
	public String getTestid() {
		return testid;
	}
	public void setTestid(String testid) {
		this.testid = testid;
	}
	private String optionC;
	private String optionD;
	private String correctOption;
	@DBRef
	private Test test;
	
	public QuestionDTO getDto() {
		QuestionDTO dto=new QuestionDTO();
		dto.setId(id);
		dto.setQuestionText(questionText);
		dto.setOptionA(optionA);
		dto.setOptionB(optionB);
		dto.setOptionC(optionC);
		dto.setOptionD(optionD);
		dto.setCorrectOption(correctOption);
		return dto;
	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public String getQuestionText() {
		return questionText;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getCorrectOption() {
		return correctOption;
	}
	public void setCorrectOption(String correctOption) {
		this.correctOption = correctOption;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	
}
