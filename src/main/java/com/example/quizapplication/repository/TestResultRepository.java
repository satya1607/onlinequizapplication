package com.example.quizapplication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.TestResult;

@Repository
public interface TestResultRepository extends MongoRepository<TestResult,Long> {

	List<TestResult> findAllByUserId(Long userId);
	
}
