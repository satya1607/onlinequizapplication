package com.example.quizapplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.QuestionDTO;

@Document(collection = "Question")
public class Question {
	 @Id
	    private String id; // numeric ID
	    private String testId; // reference to test numeric ID
	    private String questionText;
	    private String optionA;
	    private String optionB;
	    private String optionC;
	    private String optionD;
	    private String correctOption;
	    private String testSequenceNumber;
	    
	    public String getTestSequenceNumber() {
			return testSequenceNumber;
		}
		public void setTestSequenceNumber(String testSequenceNumber) {
			this.testSequenceNumber = testSequenceNumber;
		}
		public TestPOJO getTest() {
			return test;
		}
		public void setTest(TestPOJO test) {
			this.test = test;
		}
		@DBRef
	    private TestPOJO test;

	    // Getters and Setters
	   
	    
	    public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	    public String getTestId() {
			return testId;
		}
		public void setTestId(String testId) {
			this.testId = testId;
		}
		public String getQuestionText() { return questionText; }
	    public void setQuestionText(String questionText) { this.questionText = questionText; }
	    public String getOptionA() { return optionA; }
	    public void setOptionA(String optionA) { this.optionA = optionA; }
	    public String getOptionB() { return optionB; }
	    public void setOptionB(String optionB) { this.optionB = optionB; }
	    public String getOptionC() { return optionC; }
	    public void setOptionC(String optionC) { this.optionC = optionC; }
	    public String getOptionD() { return optionD; }
	    public void setOptionD(String optionD) { this.optionD = optionD; }
	    public String getCorrectOption() { return correctOption; }
	    public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }
}
