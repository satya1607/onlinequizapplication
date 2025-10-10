package com.example.quizapplication.rep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.repository.TestResultRepository;

class TestResultRepositoryTest {

	@Mock
    private TestResultRepository testResultRepository;

    private TestResult result1;
    private TestResult result2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

//        result1 = new TestResult();
//        result1.setId(1L);
//    
//
//        result2 = new TestResult();
//        result2.setId("2");
//        result2.setUser(100L);
    }

    @Test
    void testFindAllByUserId_WhenResultsExist_ShouldReturnList() {
        when(testResultRepository.findAllByUserId(100L)).thenReturn(Arrays.asList(result1, result2));

        List<TestResult> results = testResultRepository.findAllByUserId(100L);

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(testResultRepository, times(1)).findAllByUserId(100L);
    }

    @Test
    void testFindAllByUserId_WhenNoResults_ShouldReturnEmptyList() {
        when(testResultRepository.findAllByUserId(200L)).thenReturn(Collections.emptyList());

        List<TestResult> results = testResultRepository.findAllByUserId(200L);

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(testResultRepository, times(1)).findAllByUserId(200L);
    }

}
