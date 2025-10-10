package com.example.quizapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.exception.UserNotFoundException;
import com.example.quizapplication.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;


	@InjectMocks
	private UserServiceImpl userService;


	@BeforeEach
	void setUp() {
	MockitoAnnotations.openMocks(this);
	}


	@Test
	void testCreateAdminUser_WhenAdminDoesNotExist_ShouldCreateAdmin() {
	when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(null);


	userService.createAdminUser();
	verify(userRepository, times(1)).save(any(User.class));
	}


	@Test
	void testCreateAdminUser_WhenAdminExists_ShouldNotCreateAdmin() {
	User admin = new User();
	admin.setRole(UserRole.ADMIN);
	when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(admin);


	userService.createAdminUser();


	verify(userRepository, never()).save(any(User.class));
	}


	@Test
	void testHasUserWithEmail_WhenUserExists_ShouldReturnTrue() {
	when(userRepository.findFirstByEmail("test@example.com")).thenReturn(new User());


	assertTrue(userService.hasUserWithEmail("test@example.com"));
	}
	@Test
	void testHasUserWithEmail_WhenUserDoesNotExist_ShouldReturnFalse() {
	when(userRepository.findFirstByEmail("notfound@example.com")).thenReturn(null);


	assertFalse(userService.hasUserWithEmail("notfound@example.com"));
	}


	@Test
	void testCreateUser_ShouldAssignUserRoleAndSave() {
	User user = new User();
	user.setRole(null);


	userService.createUser(user);


	assertEquals(UserRole.USER, user.getRole());
	verify(userRepository, times(1)).save(user);
	}


	@Test
	void testLogin_WhenUserExists_ShouldReturnUser() {
	User user = new User();
	user.setEmail("login@example.com");
	when(userRepository.findByEmail("login@example.com")).thenReturn(Optional.of(user));
	User result = userService.login("login@example.com");
	assertNotNull(result);
	assertEquals("login@example.com", result.getEmail());
	}


	@Test
	void testLogin_WhenUserNotFound_ShouldThrowException() {
	when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());


	assertThrows(UserNotFoundException.class, () -> userService.login("missing@example.com"));
	}
	}

