package com.example.quizapplication.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapplication.dto.QuestionDTO;
import com.example.quizapplication.dto.QuestionResponse;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestDTO;
import com.example.quizapplication.dto.TestDetailsDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.entity.User;
import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.repository.TestResultRepository;
import com.example.quizapplication.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestRepository testRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private TestResultRepository testResultRepository;
	@Autowired
	private UserRepository userRepository;
	
	public void createTest(TestPOJO testPOJO) {
//		Test test=new Test();
//		test.setId(dto.getId());
//		test.setTitle(dto.getTitle());
//		test.setDescription(dto.getDescription());
//		test.setTime(dto.getTime());
		
		 testRepository.save(testPOJO);
	}
	public Question addQuestionInTest(Question dto) {
//		Optional<Test> optionalTest=testRepository.findById(id);
//		if(optionalTest.isPresent()) {
//			Question question=new Question();
//			question.setTest(optionalTest.get());
//			question.setQuestionText(dto.getQuestionText());
//			question.setOptionA(dto.getOptionA());
//			question.setOptionB(dto.getOptionB());
//			question.setOptionC(dto.getOptionC());
//			question.setOptionD(dto.getOptionD());
//			question.setCorrectOption(dto.getCorrectOption());
			
			return questionRepository.save(dto);
		}
//		throw new EntityNotFoundException("Test Not Found");
//	}
	public List<TestPOJO> getAllTests(){
		return testRepository.findAll();
//				.stream().peek(
//				test->test.setTime(test.getQuestions().size()*test.getTime())).
//				collect(Collectors.toList());
	}
	public Optional<TestPOJO> getAllQuestionsByTest(String id) {
		
				return testRepository.findById(id);
				
//		TestDetailsDTO testDetailsDTO=new TestDetailsDTO();
//		if(optionalTest.isPresent()) {
//			TestDTO testDTO=optionalTest.get().getDto();
//			testDTO.setTime(optionalTest.get().getTime()*optionalTest.get().getQuestions().size());
//			
//			testDetailsDTO.setTestDTO(testDTO);
//			testDetailsDTO.setQuestions(optionalTest.get().getQuestions().stream().
//					map(Question::getDto).toList());
//			
//			return testDetailsDTO;
//		}
//		return testDetailsDTO;
//		return optionalTest;
	}
	
	public TestResultDTO submitTest(SubmitTestDTO request) {
		TestPOJO testPOJO=testRepository.findById(request.getTestId()).orElseThrow(()->new EntityNotFoundException("Test Not Found"));
		User user=userRepository.findById(request.getUserId()).orElseThrow(()->new EntityNotFoundException("User Not Found"));
		
		int correctAnswers=0;
		for(QuestionResponse response:request.getResponses()) {
			Question question=questionRepository.findById(response.getQuestionId())
					.orElseThrow(()->new EntityNotFoundException("Question Not Found"));
		if(question.getCorrectOption().equals(response.getSelectedOption())) {
			correctAnswers++;
		}
		}
		int totalQuestions=testPOJO.getQuestions().size();
		double percentage=((double)correctAnswers/totalQuestions)*100;
		 
		TestResult testResult=new TestResult();
		testResult.setTestPOJO(testPOJO);
		testResult.setUser(user);
		testResult.setTotalQuestions(totalQuestions);
		testResult.setCorrectAnswers(correctAnswers);
		testResult.setPercentage(percentage);
		
		return testResultRepository.save(testResult).getDto();
	}

	public List<TestResult> getAllTestResults(){
		return testResultRepository.findAll();
//				.stream().map(TestResult::getDto)
//				.collect(Collectors.toList());
	}
	public List<TestResultDTO> getAllTestResultsOfUser(Long userId){
		return testResultRepository.findAllByUserId(userId).stream().map(TestResult::getDto)
				.collect(Collectors.toList());
	}
	
	}


