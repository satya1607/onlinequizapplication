package com.example.quizapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.TestRepository;
import com.example.quizapplication.repository.TestResultRepository;
import com.example.quizapplication.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.quizapplication.dto.QuestionResponse;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.entity.TestResult;
//import com.example.quizapplication.entity.*;
import com.example.quizapplication.entity.User;

class TestServiceTest {

	@Mock 
	private TestRepository testRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock 
    private TestResultRepository testResultRepository;
    @Mock 
    private UserRepository userRepository;

    @InjectMocks
    private TestServiceImpl service; // replace with your actual service class name


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTest_ShouldSaveTest() {
        TestPOJO test = new TestPOJO();
        when(testRepository.save(test)).thenReturn(test);

        service.createTest(test);

        verify(testRepository, times(1)).save(test);
    }

    @Test
    void testAddQuestionInTest_ShouldSaveQuestion() {
        Question question = new Question();
        when(questionRepository.save(question)).thenReturn(question);

        Question result = service.addQuestionInTest(question);

        assertNotNull(result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testGetAllTests_ShouldReturnList() {
        List<TestPOJO> tests = Arrays.asList(new TestPOJO(), new TestPOJO());
        when(testRepository.findAll()).thenReturn(tests);

        List<TestPOJO> result = service.getAllTests();

        assertEquals(2, result.size());
        verify(testRepository, times(1)).findAll();
    }

    @Test
    void testGetAllQuestionsByTest_WhenFound() {
        TestPOJO test = new TestPOJO();
        when(testRepository.findById("1")).thenReturn(Optional.of(test));

        Optional<TestPOJO> result = service.getAllQuestionsByTest("1");

        assertTrue(result.isPresent());
        verify(testRepository, times(1)).findById("1");
    }

    @Test
    void testGetAllQuestionsByTest_WhenNotFound() {
        when(testRepository.findById("1")).thenReturn(Optional.empty());

        Optional<TestPOJO> result = service.getAllQuestionsByTest("1");

        assertFalse(result.isPresent());
    }

    @Test
    void testSubmitTest_WhenAllCorrect_ShouldSaveResult() {
        // mock test
        TestPOJO test = new TestPOJO();
        Question q1 = new Question(); q1.setId("q1"); q1.setCorrectOption("A");
        Question q2 = new Question(); q2.setId("q2"); q2.setCorrectOption("B");
        test.setQuestions(Arrays.asList(q1, q2));

        SubmitTestDTO request = new SubmitTestDTO();
        request.setTestId("t1");
        request.setUserId("1");
        QuestionResponse r1 = new QuestionResponse(); r1.setQuestionId("q1"); r1.setSelectedOption("A");
        QuestionResponse r2 = new QuestionResponse(); r2.setQuestionId("q2"); r2.setSelectedOption("B");
        request.setResponses(Arrays.asList(r1, r2));

        User user = new User();

        when(testRepository.findById("t1")).thenReturn(Optional.of(test));
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(questionRepository.findById("q1")).thenReturn(Optional.of(q1));
        when(questionRepository.findById("q2")).thenReturn(Optional.of(q2));
        when(testResultRepository.save(any(TestResult.class))).thenAnswer(i -> {
            TestResult saved = i.getArgument(0);
            return saved; // return same object
        });

        TestResultDTO result = service.submitTest(request);

        assertEquals(2, result.getCorrectAnswers());
        assertEquals(100.0, result.getPercentage());
        verify(testResultRepository, times(1)).save(any(TestResult.class));
    }

    @Test
    void testSubmitTest_WhenUserNotFound_ShouldThrowException() {
        SubmitTestDTO request = new SubmitTestDTO();
        request.setTestId("t1");
        request.setUserId("1");

        when(testRepository.findById("t1")).thenReturn(Optional.of(new TestPOJO()));
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.submitTest(request));
    }

    @Test
    void testGetAllTestResults_ShouldReturnList() {
        List<TestResult> results = Arrays.asList(new TestResult(), new TestResult());
        when(testResultRepository.findAll()).thenReturn(results);

        List<TestResult> response = service.getAllTestResults();

        assertEquals(2, response.size());
        verify(testResultRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTestResultsOfUser_ShouldReturnDtos() {
    	
    	 TestPOJO test = new TestPOJO();
    	    test.setId("t1");
    	    test.setTitle("Sample Test");

    	    // wrap test into TestPOJO (or however your entity builds it)
    	    TestPOJO testPOJO = new TestPOJO();
    	    testPOJO.setTitle("Sample Test");

    	    // prepare User
    	    User user = new User();
    	    user.setId("1");
    	    user.setEmail("user@example.com");

    	    // prepare TestResults
    	    TestResult r1 = new TestResult();
    	    r1.setCorrectAnswers(5);
    	    r1.setTestPOJO(testPOJO);   // ✅ important
    	    r1.setUser(user);
    	    
    	    TestResult r2 = new TestResult();
    	    r2.setCorrectAnswers(3);
    	    r2.setTestPOJO(testPOJO);   // ✅ important
    	    r2.setUser(user);
    	
//        TestResult r1 = new TestResult(); r1.setCorrectAnswers(5);
//        TestResult r2 = new TestResult(); r2.setCorrectAnswers(3);

        when(testResultRepository.findAllByUserId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<TestResultDTO> result = service.getAllTestResultsOfUser(1L);

        assertEquals(2, result.size());
        assertEquals("Sample Test", result.get(0).getTestName());
        verify(testResultRepository, times(1)).findAllByUserId(1L);
    }

}
