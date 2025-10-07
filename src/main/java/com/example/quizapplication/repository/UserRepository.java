package com.example.quizapplication.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;

@Repository
public interface UserRepository extends MongoRepository<User,String>{

	User findByRole(UserRole role);
	
	User findFirstByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
