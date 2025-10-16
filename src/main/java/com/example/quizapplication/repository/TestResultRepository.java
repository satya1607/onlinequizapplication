package com.example.quizapplication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.TestResult;

@Repository
public interface TestResultRepository extends MongoRepository<TestResult,String> {

	List<TestResult> findAllByUserId(Long userId);
	List<TestResult> findByUserName(String userName);
	
}
