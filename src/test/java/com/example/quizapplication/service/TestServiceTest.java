package com.example.quizapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.repository.TestResultRepository;
import com.example.quizapplication.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.quizapplication.config.SequenceGeneratorService;
import com.example.quizapplication.dto.QuestionResponse;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
//import com.example.quizapplication.entity.*;
import com.example.quizapplication.entity.User;
import com.example.quizapplication.entity.Counter;

class TestServiceTest {

	@InjectMocks
    private TestServiceImpl testService;

    @Mock
    private TestRepository testRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TestResultRepository testResultRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 1️⃣ Test creating a test with next sequence number
    @Test
    void testCreateTest_AssignsNextSequenceNumber() {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setTitle("Java Test");

        TestPOJO lastTest = new TestPOJO();
        lastTest.setSequenceNumber(3);

        when(mongoTemplate.findOne(any(Query.class), eq(TestPOJO.class)))
                .thenReturn(lastTest);

        testService.createTest(testPOJO);

        assertEquals(4, testPOJO.getSequenceNumber());
        verify(testRepository, times(1)).save(testPOJO);
    }

    // 2️⃣ Test createTest when there are no existing tests
    @Test
    void testCreateTest_FirstTestSequenceNumberIsOne() {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setTitle("First Test");

        when(mongoTemplate.findOne(any(Query.class), eq(TestPOJO.class)))
                .thenReturn(null);

        testService.createTest(testPOJO);

        assertEquals(1, testPOJO.getSequenceNumber());
        verify(testRepository).save(testPOJO);
    }

    // 3️⃣ Test fetching a test by sequence number
    @Test
    void testGetTestBySequenceNumber_ReturnsTest() {
        TestPOJO test = new TestPOJO();
        test.setSequenceNumber(5);
        when(testRepository.findBySequenceNumber(5)).thenReturn(test);

        TestPOJO result = testService.getTestBySequenceNumber(5);

        assertNotNull(result);
        assertEquals(5, result.getSequenceNumber());
        verify(testRepository).findBySequenceNumber(5);
    }

    // 4️⃣ Test adding a question to a test
    @Test
    void testAddQuestionToTest_Success() {
        TestPOJO test = new TestPOJO();
        test.setSequenceNumber(2);
        test.setQuestions(new ArrayList<>());

        Question question = new Question();
        question.setQuestionText("What is Java?");

        when(testRepository.findBySequenceNumber(2)).thenReturn(test);
        Counter counter = new Counter("question_sequence", 1);
        when(mongoTemplate.findAndModify(any(), any(), any(), eq(Counter.class)))
                .thenReturn(counter);

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question saved = testService.addQuestionToTest(2, question);

        assertEquals("What is Java?", saved.getQuestionText());
        assertEquals(1, test.getQuestions().size());
        verify(questionRepository).save(any(Question.class));
        verify(testRepository, times(1)).save(test);
    }

    // 5️⃣ Test getAllTests
    @Test
    void testGetAllTests_ReturnsList() {
        List<TestPOJO> list = Arrays.asList(new TestPOJO(), new TestPOJO());
        when(testRepository.findAll()).thenReturn(list);

        List<TestPOJO> result = testService.getAllTests();

        assertEquals(2, result.size());
        verify(testRepository).findAll();
    }

    // 6️⃣ Test submitTest() calculates results correctly
    @Test
    void testSubmitTest_CalculatesResults() {
        int testSeq = 1;

        TestPOJO test = new TestPOJO();
        test.setTitle("Java Basics");
        when(testRepository.findBySequenceNumber(testSeq)).thenReturn(test);

        Question q1 = new Question();
        q1.setId("1");
        q1.setCorrectOption("A");

        Question q2 = new Question();
        q2.setId("2");
        q2.setCorrectOption("B");

        List<Question> questions = Arrays.asList(q1, q2);
        when(questionRepository.findByTestSequenceNumber(testSeq)).thenReturn(questions);

        Map<String, String> answers = new HashMap<>();
        answers.put("question_1", "A");
        answers.put("question_2", "C");

        TestResult mockResult = new TestResult();
        when(testResultRepository.save(any(TestResult.class))).thenReturn(mockResult);

        TestResult result = testService.submitTest(testSeq, answers);

        assertNotNull(result);
        verify(testRepository).findBySequenceNumber(testSeq);
        verify(questionRepository).findByTestSequenceNumber(testSeq);
        verify(testResultRepository).save(any(TestResult.class));
    }

    // 7️⃣ Test getAllTestResults
    @Test
    void testGetAllTestResults_ReturnsList() {
        when(testResultRepository.findAll()).thenReturn(Arrays.asList(new TestResult(), new TestResult()));
        List<TestResult> results = testService.getAllTestResults();
        assertEquals(2, results.size());
        verify(testResultRepository).findAll();
    }
}
