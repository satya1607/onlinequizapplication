package com.example.quizapplication.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.exception.UserNotFoundException;
import com.example.quizapplication.repository.UserRepository;
import com.example.quizapplication.service.TestService;
import com.example.quizapplication.service.UserService;
//import com.example.realestateproject.entity.UserInfo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
   
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestService testService;
	
	 @GetMapping("/register")
	    public String showRegisterForm(Model model){
	        model.addAttribute("user",new User());
	        return "register";
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
		return "redirect:/login";
	}
	
	 @GetMapping("/login")
	    public String showLoginForm(){
	        return "login";
	    }
	 

	 
		
//		if(dbUser==null) 
//			return "Wrong conditionals";
//		if(dbUser!=null) {
//		if(UserRole.ADMIN != null)){
//		return "admindashboard";
//		}
//		else {
//			return "userdashboard";
//		}
//		}
//		return null;
		
//	}
	 @GetMapping("/userdashboard")
	    public String showUserDashboard(Model model){
		 List<TestPOJO> list = testService.getAllTests();
	        model.addAttribute("list", list);
	        return "userdashboard";
	    }
	 
	 @PostMapping("/login")
	 public String login(@RequestParam String email,
	                     @RequestParam String password,
	                     @RequestParam String role,
	                     Model model,
	                     HttpSession session) {

	     try {
	         // Fetch user from DB
	         User dbUser = userService.login(email);

	         // Check password
	         if (!dbUser.getPassword().equals(password)) {
	             model.addAttribute("error", "Invalid password!");
	             return "login";
	         }

	         // Check role
	         if (!dbUser.getRole().name().equals(role)) {
	             model.addAttribute("error", "Role mismatch!");
	             return "login";
	         }

	         // Save user info in session (optional)
	         session.setAttribute("loggedInUser", dbUser);

	         // Redirect based on role
	         if (dbUser.getRole() == UserRole.ADMIN) {
	             return "redirect:/admindashboard";
	         } else {
	             return "redirect:/userdashboard";
	         }

	     } catch (Exception e) {
	         model.addAttribute("error", "User not found!");
	         return "login";
	     }
	 }
}