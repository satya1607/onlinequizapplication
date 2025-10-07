package com.example.quizapplication.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.quizapplication.enums.UserRole;

@Document(collection="User")
public class User {
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	@Id
	private String id;
	
	private String email;
	private String password;
	private String name;
	private UserRole role;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
//	public String getQuizId() {
//		return quizId;
//	}
//	public void setQuizId(String quizId) {
//		this.quizId = quizId;
//	}
//	public String getAnswers() {
//		return answers;
//	}
//	public void setAnswers(String answers) {
//		this.answers = answers;
//	}
//	public Integer getScore() {
//		return score;
//	}
//	public void setScore(Integer score) {
//		this.score = score;
//	}

}
