package com.example.quizapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.exception.UserNotFoundException;
import com.example.quizapplication.repository.UserRepository;
import com.example.quizapplication.service.UserService;
//import com.example.realestateproject.entity.UserInfo;

import jakarta.validation.Valid;

@Controller
public class UserController {
   
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	 @GetMapping("/signup")
	    public String showRegisterForm(Model model){
	        model.addAttribute("user",new User());
	        return "signup";
	    }
	    
	
	@PostMapping("/register")
	public String signUpUser(@ModelAttribute User user){
		System.out.println("Method called");
//		if(userService.hasUserWithEmail(user.getEmail())) {
//			throw new UserNotFoundException("User Not Found");
//			
//		}
		userService.createUser(user);
//		if(createdUser==null) {
//			return "User not created, come again later";
//			
//		}
//		return new ResponseEntity<>(createdUser,HttpStatus.OK);
		return "/login";
	}
	
	 @GetMapping("/login")
	    public String showLoginForm(){
	        return "login";
	    }
	 
	 @GetMapping("/admindashboard")
	    public String showAdminDashboard(){
	        return "admindashboard";
	    }
	
	@PostMapping({"/login"})
	public String login(@ModelAttribute User user){
		User dbUser=userService.login(user.getEmail());
//		if(dbUser==null) 
//			return "Wrong conditionals";
//		if(dbUser!=null) {
//		if(UserRole.ADMIN != null)){
		return "admindashboard";
//		}
//		else {
//			return "userdashboard";
//		}
//		}
//		return null;
		
	}
	
}