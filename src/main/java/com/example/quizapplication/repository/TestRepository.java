package com.example.quizapplication.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.TestPOJO;

@Repository
public interface TestRepository extends MongoRepository<TestPOJO, String> {

	 TestPOJO findBySequenceNumber(int sequenceNumber);
}
