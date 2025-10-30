package com.example.quizapplication.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.dto.TestDTO;

@Document(collection="TestPOJO")
public class TestPOJO {
	
	@Id
	private String id;  // numeric ID
    private String title;
    private String description;
    private int time; // in minutes
    
    @DBRef
    private List<Question> questions = new ArrayList<>();
    @Indexed(unique = true)
    private int sequenceNumber;

    // Getters and Setters
    
    
	public String getTitle() {
        return title;
    }
    public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
