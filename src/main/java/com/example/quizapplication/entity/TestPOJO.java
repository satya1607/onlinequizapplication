package com.example.quizapplication.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.TestDTO;

@Document(collection="TestPOJO")
public class TestPOJO {
	
	@Id
    private String id;

    @Indexed(unique = true) 
    private String testid; 
	
	 
	
    public String getTestid() {
		return testid;
	}
	public void setTestid(String testid) {
		this.testid = testid;
	}


	private String title;
	private String description;
	private Long time;

	private List<Question> questions;
	
	
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
//	public String get_id() {
//		return _id;
//	}
//	public void set_id(String _id) {
//		this._id = _id;
//	}
	
	public String getTitle() {
		return title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	public TestDTO getDto() {
		TestDTO testDTO=new TestDTO();
	     testDTO.setId(id);
	     testDTO.setTitle(title);
	     testDTO.setDescription(description);
	     testDTO.setTime(time);
	     return testDTO;
	}

}
