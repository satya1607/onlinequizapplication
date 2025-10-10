package com.example.quizapplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.quizapplication.controller.UserDetails;
//import com.example.quizapplication.controller.UserInfo;
//import com.example.quizapplication.controller.UsernameNotFoundException;
import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.exception.UserNotFoundException;
import com.example.quizapplication.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{
     
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void createAdminUser() {
		User optionalUser=userRepository.findByRole(UserRole.ADMIN);
		if(optionalUser==null) {
			User user=new User();
//			user.setName("Admin");
//			user.setEmail("admin@gmail.com");
//			user.setPassword("admin");
			user.setRole(UserRole.ADMIN);
		userRepository.save(user);
		}
	}
	
	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email)!=null;
	
	}
	public void createUser(User user) {
		user.setRole(UserRole.USER);
		userRepository.save(user);
	}
	
	public User login(String email) {
		User user=userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found in database."));
		
	   return user;
		
	}
	
	
}

