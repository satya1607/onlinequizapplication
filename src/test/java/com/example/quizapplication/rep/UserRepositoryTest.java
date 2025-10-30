package com.example.quizapplication.rep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.BeanDefinitionDsl.Role;

import com.example.quizapplication.entity.User;
import com.example.quizapplication.enums.UserRole;
import com.example.quizapplication.repository.UserRepository;

@DataMongoTest
class UserRepositoryTest {

   	 @Mock
	  private UserRepository userRepository;

	  private User user;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setRole(UserRole.USER);
    }

	@Test
    void testFindByRole_ShouldReturnUser() {
        when(userRepository.findByRole(UserRole.USER)).thenReturn(user);

        User result = userRepository.findByRole(UserRole.USER);

        assertNotNull(result);
        assertEquals(UserRole.USER, result.getRole());
        verify(userRepository, times(1)).findByRole(UserRole.USER);
    }

	 @Test
	    void testFindFirstByEmail_ShouldReturnUser() {
	        when(userRepository.findFirstByEmail("test@example.com")).thenReturn(user);

	        User result = userRepository.findFirstByEmail("test@example.com");

	        assertNotNull(result);
	        assertEquals("test@example.com", result.getEmail());
	        verify(userRepository, times(1)).findFirstByEmail("test@example.com");
	    }	
	
	 @Test
	    void testFindByEmail_ShouldReturnOptionalUser() {
	        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

	        Optional<User> result = userRepository.findByEmail("test@example.com");

	        assertTrue(result.isPresent());
	        assertEquals("test@example.com", result.get().getEmail());
	        verify(userRepository, times(1)).findByEmail("test@example.com");
	    }
	
	 @Test
	    void testFindByEmail_WhenUserNotFound_ShouldReturnEmptyOptional() {
	        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

	        Optional<User> result = userRepository.findByEmail("notfound@example.com");

	        assertFalse(result.isPresent());
	        verify(userRepository, times(1)).findByEmail("notfound@example.com");
	    }
	

}
