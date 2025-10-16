package com.example.quizapplication.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quizapplication.dto.QuestionDTO;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestDTO;
import com.example.quizapplication.dto.TestDetailsDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.service.TestService;

import jakarta.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/admindashboard")
    public String getAdminDashboard(Model model) {
        List<TestPOJO> list = testService.getAllTests();
        model.addAttribute("list", list);
        return "admindashboard";
    }

    @GetMapping("/createTest")
    public String showCreateTestForm(Model model) {
        model.addAttribute("test", new TestPOJO());
        return "createtest";
    }

    @PostMapping("/createTest")
    public String createTest(@ModelAttribute TestPOJO test) {
        testService.createTest(test);
        return "redirect:/api/test/admindashboard";
    }

    @GetMapping("/question/{sequenceNumber}")
    public String showAddQuestionForm(@PathVariable int sequenceNumber, Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("sequenceNumber", sequenceNumber);
        return "addquestion";
    }

    @PostMapping("/question/{sequenceNumber}")
    public String addQuestionToTest(@PathVariable int sequenceNumber, @ModelAttribute Question question) {
        testService.addQuestionToTest(sequenceNumber, question);
        return "redirect:/api/test/admindashboard";
    }

    @GetMapping("/viewtest/{sequenceNumber}")
    public String viewTest(@PathVariable int sequenceNumber, Model model) {
        List<Question> questions = testService.getQuestionsByTestNumber(sequenceNumber);
        model.addAttribute("questions", questions);
        return "viewtest";
    }
    @GetMapping("/taketest/{sequenceNumber}")
    public String takeTest(@PathVariable int sequenceNumber, Model model) {
        TestPOJO test = testService.getTestBySequenceNumber(sequenceNumber);
        System.out.println("➡️ Test sequence number: " + test.getSequenceNumber());
        List<Question> questions = testService.getQuestionsByTestNumber(sequenceNumber);

        System.out.println("Loaded Test Sequence Number: " + test.getSequenceNumber());
        model.addAttribute("test", test);
        model.addAttribute("questions", test.getQuestions());
        return "taketest";
    }
    @PostMapping("/submit-test/{sequenceNumber}")
    public String submitTest(
            @PathVariable int sequenceNumber,
            @RequestParam Map<String, String> answers,
            Model model) {

        TestResult result = testService.submitTest(sequenceNumber,answers);
//        System.out.println("Loaded Test Sequence Number: " + test.getSequenceNumber());
        model.addAttribute("result", result);

        return "viewresult"; // the result page
    }
    
    
    @GetMapping("/viewResults")
    public String viewResults(Model model) {
        List<TestResult> results = testService.getAllTestResults();
        model.addAttribute("results", results);
        return "viewresult"; // Thymeleaf template name
    }
}