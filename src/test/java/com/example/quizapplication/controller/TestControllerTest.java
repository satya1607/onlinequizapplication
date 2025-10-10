package com.example.quizapplication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(TestController.class)
class TestControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @MockBean
    private TestRepository testRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- Thymeleaf View Endpoints ----------

    @Test
    void showCreateTestForm_shouldReturnCreateTestView() throws Exception {
        mockMvc.perform(get("/api/test/createTest"))
               .andExpect(status().isOk())
               .andExpect(view().name("createtest"))
               .andExpect(model().attributeExists("test"));
    }

    @Test
    void createTest_shouldSaveTestAndReturnAdminDashboard() throws Exception {
        TestPOJO test = new TestPOJO();
        when(testRepository.save(any(TestPOJO.class))).thenReturn(test);

        mockMvc.perform(post("/api/test/createTest")
               .flashAttr("test", test))
               .andExpect(status().isOk())
               .andExpect(view().name("admindashboard"));

        verify(testRepository, times(1)).save(any(TestPOJO.class));
    }

    @Test
    void addQuestion_shouldReturnAddQuestionView() throws Exception {
        mockMvc.perform(get("/api/test/question"))
               .andExpect(status().isOk())
               .andExpect(view().name("addquestion"))
               .andExpect(model().attributeExists("dto"));
    }

    @Test
    void addQuestionInTest_shouldCallServiceAndReturnAdminDashboard() throws Exception {
        Question question = new Question();

        mockMvc.perform(post("/api/test/postquestion")
                .flashAttr("dto", question))
                .andExpect(status().isOk())
                .andExpect(view().name("admindashboard"));

        verify(testService, times(1)).addQuestionInTest(any(Question.class));
    }

    @Test
    void getAllTest_shouldReturnAdminDashboardWithTests() throws Exception {
        List<TestPOJO> tests = Arrays.asList(new TestPOJO(), new TestPOJO());
        when(testService.getAllTests()).thenReturn(tests);

        mockMvc.perform(get("/api/test/admindashboard"))
               .andExpect(status().isOk())
               .andExpect(view().name("admindashboard"))
               .andExpect(model().attributeExists("list"));
    }

//    @Test
//    void getAllQuestions_shouldReturnViewTest() throws Exception {
//    	TestPOJO test = new TestPOJO();
////        test.setQuestions(); // so Thymeleaf finds it
//        when(testService.getAllQuestionsByTest("1")).thenReturn(Optional.of(test));
//
//        mockMvc.perform(get("/api/test/viewtest/1"))
//               .andExpect(status().isOk())
//               .andExpect(view().name("viewtest"))
//               .andExpect(model().attributeExists("list"));
//    	
//        
//    }

//    @Test
//    void getAllTestResults_shouldReturnViewResult() throws Exception {
//        List<TestResult> results = Arrays.asList(new TestResult());
//        when(testService.getAllTestResults()).thenReturn(results);
//
//        mockMvc.perform(get("/api/test/viewResults"))
//               .andExpect(status().isOk())
//               .andExpect(view().name("viewresult"))
//               .andExpect(model().attributeExists("list"));
//    }

    // ---------- REST Endpoints ----------

    @Test
    void submitTest_shouldReturnOkWithServiceResponse() throws Exception {
    	SubmitTestDTO submitDto = new SubmitTestDTO();
        TestResultDTO resultDto = new TestResultDTO();

        when(testService.submitTest(any(SubmitTestDTO.class))).thenReturn(resultDto);

        mockMvc.perform(post("/api/test/submit-test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(submitDto)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(resultDto)));
    
    }

    @Test
    void getAllTestResultsOfUser_shouldReturnOkWithServiceResponse() throws Exception {
    	List<TestResultDTO> resultsDto = Arrays.asList(new TestResultDTO());
        when(testService.getAllTestResultsOfUser(1L)).thenReturn(resultsDto);

        mockMvc.perform(get("/api/test/test-result/1"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(resultsDto)));
    	
    	
    }

//    @Test
//    void getAllTestResultsOfUser_shouldReturnBadRequestOnException() throws Exception {
//        when(testService.getAllTestResultsOfUser(1L)).thenThrow(new RuntimeException("User not found"));
//
//        mockMvc.perform(get("/api/test/test-result/1"))
//               .andExpect(status().isBadRequest())
//               .andExpect(content().string("User not found"));
//    }

}
