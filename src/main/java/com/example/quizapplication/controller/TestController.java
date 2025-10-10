package com.example.quizapplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quizapplication.dto.QuestionDTO;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestDTO;
import com.example.quizapplication.dto.TestDetailsDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.service.TestService;


@Controller
@RequestMapping("api/test")
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private TestRepository testRepository;
	
	@GetMapping("/createTest")
	public String showCreateTestForm(Model model) {
		model.addAttribute("test", new TestPOJO());
		return "createtest";
	}
	@PostMapping("/createTest")
	public String createTest(@ModelAttribute TestPOJO testPOJO){
		
		testRepository.save(testPOJO);
//		testService.createTest(test);
//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//		}
		return "admindashboard";
	}
	@GetMapping("/question")
	public String addQuestion(Model model) {
		model.addAttribute("dto", new Question());
		return "addquestion";
	}
	
	@PostMapping("/postquestion")
	public String addQuestionInTest(@ModelAttribute Question dto){
		testService.addQuestionInTest(dto);
		
		return "admindashboard";
		
	}
	@GetMapping("/admindashboard")
	public String getAllTest(Model model,TestPOJO testPOJO){
//		List<PropertyDetails> list = service.getAllProperties();
//		  model.addAttribute("list", list);}
			List<TestPOJO> list=testService.getAllTests();
		    model.addAttribute("list",list);
		    System.out.println(list);
			return "admindashboard";
	}
	
	@GetMapping("/viewtest/{id}")
	public String getAllQuestions(@PathVariable String id,Model model){
		 Optional<TestPOJO> questions =testService.getAllQuestionsByTest(id);
		model.addAttribute("questions",questions);
		return "viewtest";
		
	}
	
	@PostMapping("/submit-test")
	public ResponseEntity<?> submitTest(@RequestBody SubmitTestDTO dto){
		try {
			return new ResponseEntity<>(testService.submitTest(dto),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/viewResults")
	public String getAllTestResults(Model model){
		List<TestResult> list=testService.getAllTestResults();
		model.addAttribute("list",list);
		return "viewresult";
		
	}
	
	@GetMapping("/test-result/{id}")
	public ResponseEntity<?> getAllTestResultsOfUser(@PathVariable Long id){
		try {
			return new ResponseEntity<>(testService.getAllTestResultsOfUser(id),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
}
