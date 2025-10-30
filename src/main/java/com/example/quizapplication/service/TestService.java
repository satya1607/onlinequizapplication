package com.example.quizapplication.service;

import java.util.List;
import java.util.Map;
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
	   Question addQuestionToTest(int sequenceNumber, Question question);
	   TestPOJO getTestBySequenceNumber(int sequenceNumber);
	List<TestPOJO> getAllTests();
	List<Question> getQuestionsByTestNumber(int sequenceNumber);
	TestResult submitTest(int testSequenceNumber, Map<String, String> answers);
	List<TestResult> getAllTestResults();
	List<TestResultDTO> getAllTestResultsOfUser(String userId);
}
