package com.example.quizapplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
	

	public void createUser(User user) {
//		user.setRole(UserRole.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public User login(String email) {
		User user=userRepository.findByEmail(email)
				.orElseThrow(() ->  new UserNotFoundException("User not found in database"));
		
	 return user;
		
	}
	
	
}

