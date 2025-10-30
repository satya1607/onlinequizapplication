package com.example.quizapplication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebMvcTest(TestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TestControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private TestService testService;
        @MockBean
        private TestRepository testRepository;
        @MockBean
        private QuestionRepository questionRepository;
        
	    private TestPOJO testPOJO;
	    private Question question;
	    private TestResult testResult;

	    @BeforeEach
	    void setUp() {
	        // Sample Test
	        testPOJO = new TestPOJO();
	        testPOJO.setSequenceNumber(1);
	        testPOJO.setTitle("Sample Test");
	        testPOJO.setQuestions(new ArrayList<>());

	        // Sample Question
	        question = new Question();
	        question.setId("1");
	        question.setQuestionText("Sample Question?");
	        question.setOptionA("A");
	        question.setOptionB("B");
	        question.setOptionC("C");
	        question.setOptionD("D");
	        question.setCorrectOption("A");
	        question.setTest(testPOJO);
	        testPOJO.getQuestions().add(question);

	        // Sample TestResult
	        testResult = new TestResult();
	        testResult.setTestName("Sample Test");
	        testResult.setUserName("John Doe");
	        testResult.setTotalQuestions(1);
	        testResult.setCorrectAnswers(1);
	        testResult.setPercentage(100.0);
	    }

	    @Test
	    void testGetAdminDashboard() throws Exception {
	        when(testService.getAllTests()).thenReturn(Collections.singletonList(testPOJO));

	        mockMvc.perform(get("/api/test/admindashboard"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("list"))
	                .andExpect(view().name("admindashboard"));
	    }

	    @Test
	    void testShowCreateTestForm() throws Exception {
	        mockMvc.perform(get("/api/test/createTest"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("test"))
	                .andExpect(view().name("createtest"));
	    }

	    @Test
	    void testShowAddQuestionForm() throws Exception {
	        mockMvc.perform(get("/api/test/question/{sequenceNumber}", 1))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("question"))
	                .andExpect(model().attributeExists("sequenceNumber"))
	                .andExpect(view().name("addquestion"));
	    }

	    @Test
	    void testAddQuestionToTest() throws Exception {
	        mockMvc.perform(post("/api/test/question/{sequenceNumber}", 1)
	                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                        .param("questionText", "Sample Question?")
	                        .param("optionA", "A")
	                        .param("optionB", "B")
	                        .param("optionC", "C")
	                        .param("optionD", "D")
	                        .param("correctOption", "A"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/api/test/admindashboard"));
	    }

	    @Test
	    void testViewTest() throws Exception {
	        when(testService.getQuestionsByTestNumber(1)).thenReturn(Collections.singletonList(question));

	        mockMvc.perform(get("/api/test/viewtest/{sequenceNumber}", 1))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("questions"))
	                .andExpect(view().name("viewtest"));
	    }

	    @Test
	    void testTakeTest() throws Exception {
	        when(testService.getTestBySequenceNumber(1)).thenReturn(testPOJO);
	        when(testService.getQuestionsByTestNumber(1)).thenReturn(Collections.singletonList(question));

	        mockMvc.perform(get("/api/test/taketest/{sequenceNumber}", 1))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("test"))
	                .andExpect(model().attributeExists("questions"))
	                .andExpect(view().name("taketest"));
	    }

	    @Test
	    void testSubmitTest() throws Exception {
	        Map<String, String> answers = new HashMap<>();
	        answers.put("question_1", "A");

	        when(testService.submitTest(anyInt(), Mockito.anyMap())).thenReturn(testResult);

	        mockMvc.perform(post("/api/test/submit-test/{sequenceNumber}", 1)
	                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                        .param("question_1", "A")
	                        .param("userName", "John Doe"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("result"))
	                .andExpect(view().name("viewresult"));
	    }

	    @Test
	    void testViewResults() throws Exception {
	        when(testService.getAllTestResults()).thenReturn(Collections.singletonList(testResult));

	        mockMvc.perform(get("/api/test/viewResults"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("results"))
	                .andExpect(view().name("viewresult"));
	    }
}
