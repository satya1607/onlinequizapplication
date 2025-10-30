package com.example.quizapplication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.repository.UserRepository;
import com.example.quizapplication.service.TestService;
import com.example.quizapplication.service.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TestService testService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testShowRegisterForm_ShouldReturnRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testSignUpUser_ShouldRedirectToLogin() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(post("/register")
                .param("email", "test@example.com")
                .param("password", "pass123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testShowLoginForm_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLogin_SuccessAdmin() throws Exception {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setPassword("encodedPass");
        user.setRole(UserRole.ADMIN);

        when(userService.login("admin@example.com")).thenReturn(user);
        when(passwordEncoder.matches("plainPass", "encodedPass")).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("email", "admin@example.com")
                .param("password", "plainPass")
                .param("role", "ADMIN")
                .sessionAttr("loggedInUser", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admindashboard"));

        verify(userService, times(1)).login("admin@example.com");
    }

    @Test
    void testLogin_SuccessUser() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPass");
        user.setRole(UserRole.USER);

        when(userService.login("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("plainPass", "encodedPass")).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("email", "user@example.com")
                .param("password", "plainPass")
                .param("role", "USER")
                .sessionAttr("loggedInUser", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userdashboard"));

        verify(userService, times(1)).login("user@example.com");
    }

    @Test
    void testLogin_InvalidPassword_ShouldReturnLoginViewWithError() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPass");
        user.setRole(UserRole.USER);

        when(userService.login("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);

        mockMvc.perform(post("/login")
                .param("email", "user@example.com")
                .param("password", "wrongPass")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testLogin_RoleMismatch_ShouldReturnLoginViewWithError() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPass");
        user.setRole(UserRole.USER);

        when(userService.login("user@example.com")).thenReturn(user);
        when(passwordEncoder.matches("plainPass", "encodedPass")).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("email", "user@example.com")
                .param("password", "plainPass")
                .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testShowUserDashboard_ShouldReturnUserDashboardView() throws Exception {
        when(testService.getAllTests()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/userdashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("userdashboard"))
                .andExpect(model().attributeExists("list"));
    }
}