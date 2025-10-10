package com.example.quizapplication.service;

import java.util.List;
import java.util.Optional;

import com.example.quizapplication.dto.QuestionDTO;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestDTO;
import com.example.quizapplication.dto.TestDetailsDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;

   public interface TestService{
	void createTest(TestPOJO testPOJO);
	Question addQuestionInTest(Question dto);
	List<TestPOJO> getAllTests();
	Optional<TestPOJO> getAllQuestionsByTest(String id);
	TestResultDTO submitTest(SubmitTestDTO request);
	List<TestResult> getAllTestResults();
	List<TestResultDTO> getAllTestResultsOfUser(Long userId);
}
