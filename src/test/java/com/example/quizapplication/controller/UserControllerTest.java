package com.example.quizapplication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.repository.UserRepository;
import com.example.quizapplication.service.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private UserService userService;

	    @MockBean
	    private UserRepository userRepository;

	    @Test
	    void testShowRegisterForm_ShouldReturnSignupView() throws Exception {
	        mockMvc.perform(get("/signup"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("signup"))
	                .andExpect(model().attributeExists("user"));
	    }

	    @Test
	    void testSignUpUser_ShouldRedirectToLogin() throws Exception {
	        User user = new User();
	        user.setEmail("test@example.com");

	        // mock service
	        doNothing().when(userService).createUser(any(User.class));

	        mockMvc.perform(post("/register")
	                .flashAttr("user", user))
	                .andExpect(status().isOk())
	                .andExpect(view().name("/login"));

	        verify(userService, times(1)).createUser(any(User.class));
	    }

	    @Test
	    void testShowLoginForm_ShouldReturnLoginView() throws Exception {
	        mockMvc.perform(get("/login"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("login"));
	    }

	    @Test
	    void testLogin_ShouldReturnAdminDashboard() throws Exception {
	        User user = new User();
	        user.setEmail("admin@example.com");

	        when(userService.login("admin@example.com")).thenReturn(user);

	        mockMvc.perform(post("/login")
	                .flashAttr("user", user))
	                .andExpect(status().isOk())
	                .andExpect(view().name("admindashboard"));

	        verify(userService, times(1)).login("admin@example.com");
	    }

	    @Test
	    void testShowAdminDashboard_ShouldReturnAdminDashboardView() throws Exception {
	        mockMvc.perform(get("/admindashboard"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("admindashboard"));
	    }

}
