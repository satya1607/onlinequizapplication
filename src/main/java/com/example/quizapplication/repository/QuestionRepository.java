package com.example.quizapplication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question,String>{

	
	List<Question> findByTestId(String testId);
	List<Question> findByTestSequenceNumber(int testSequenceNumber);
}
