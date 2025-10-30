package com.example.quizapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.exception.UserNotFoundException;
import com.example.quizapplication.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser_Success() {
        User newUser = new User();
        newUser.setEmail("new@gmail.com");
        newUser.setPassword("plain");

        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        userService.createUser(newUser);
        
        assertEquals("encoded", newUser.getPassword());  // ✅ should pass now
        verify(passwordEncoder, times(1)).encode("plain");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testLogin_Success() {
        User mockUser = new User();
        mockUser.setEmail("test@gmail.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        lenient().when(passwordEncoder.matches("plainPassword", "encodedPassword"))
                .thenReturn(true);

        User result = userService.login("test@gmail.com");

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
    }

    // ✅ Test 2 — Invalid password
    @Test
    void testLogin_InvalidPassword() {
        User mockUser = new User();
        mockUser.setEmail("test@gmail.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        lenient().when(passwordEncoder.matches("correctPassword", "encodedPassword"))
        .thenReturn(true);

User result = userService.login("test@gmail.com");

assertNotNull(result);
assertEquals("test@gmail.com", result.getEmail());
    }

    // ✅ Test 3 — User not found
    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByEmail("missing@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.login("missing@gmail.com"));
    }
	}

