package com.example.quizapplication.rep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.entity.User;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.repository.TestResultRepository;
import com.example.quizapplication.repository.UserRepository;

@DataMongoTest
class TestResultRepositoryTest {

	@Autowired
    private TestResultRepository testResultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRepository testRepository;
    
    private User user1;
    private User user2;
    private TestPOJO test1;
    private TestPOJO test2;

    @BeforeEach
    void setup() {
    	 testResultRepository.deleteAll();
        User user1 = new User();
        user1.setId("U1");
        user1.setName("Priyanka");
        User user2 = new User();
        user2.setId("U2");
        user2.setName("John");
        userRepository.saveAll(List.of(user1, user2)); 

        test1 = new TestPOJO();
        test1.setId("T1");
        test1.setTitle("Java Basics");
        
        test2 = new TestPOJO();
        test2.setId("T2");
        test2.setTitle("Spring Boot");
        testRepository.saveAll(List.of(test1, test2)); 

        // Create test results
        TestResult result1 = new TestResult();
        result1.setUser(user1);
        result1.setUserName(user1.getName());
        result1.setTestPOJO(test1);
        result1.setCorrectAnswers(8);
        result1.setTotalQuestions(10);
        result1.setPercentage(80.0);
        result1.setTestSequenceNumber(1);

        TestResult result2 = new TestResult();
        result2.setUser(user1);
        result2.setUserName(user1.getName());
        result2.setTestPOJO(test2);
        result2.setCorrectAnswers(9);
        result2.setTotalQuestions(10);
        result2.setPercentage(90.0);
        result2.setTestSequenceNumber(2);

        TestResult result3 = new TestResult();
        result3.setUser(user2);
        result3.setUserName(user2.getName());
        result3.setTestPOJO(test1);
        result3.setCorrectAnswers(6);
        result3.setTotalQuestions(10);
        result3.setPercentage(60.0);
        result3.setTestSequenceNumber(1);

        testResultRepository.saveAll(List.of(result1, result2, result3));
    }

    @Test
    @DisplayName("Should find all results by user ID")
    void testFindAllByUser_Id() {
        List<TestResult> results = testResultRepository.findAllByUserId("U1");

        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        for (TestResult r : results) {
            assertEquals("U1", r.getUser().getId());
            assertEquals("Priyanka", r.getUserName());
        }
    }

    @Test
    @DisplayName("Find test results by userName")
    void testFindByUserName() {
        List<TestResult> results = testResultRepository.findByUserName("John");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUserName()).isEqualTo("John");
        assertThat(results.get(0).getPercentage()).isEqualTo(60.0);
    }

    @Test
    @DisplayName("Return empty list for unknown user")
    void testFindAllByUserId_NoResults() {
        List<TestResult> results = testResultRepository.findAllByUserId("U99");
        assertThat(results).isEmpty();
    }

}
