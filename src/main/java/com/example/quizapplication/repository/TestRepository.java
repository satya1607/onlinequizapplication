package com.example.quizapplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.Test;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

}
