package com.example.quizapplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question,String>{

}
