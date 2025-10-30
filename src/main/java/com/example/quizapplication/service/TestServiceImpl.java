package com.example.quizapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.quizapplication.config.SequenceGeneratorService;
import com.example.quizapplication.dto.QuestionDTO;
import com.example.quizapplication.dto.QuestionResponse;
import com.example.quizapplication.dto.SubmitTestDTO;
import com.example.quizapplication.dto.TestDTO;
import com.example.quizapplication.dto.TestDetailsDTO;
import com.example.quizapplication.dto.TestResultDTO;
import com.example.quizapplication.entity.Counter;
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
	@Autowired
    private SequenceGeneratorService sequenceGenerator;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void createTest(TestPOJO testPOJO) {
	    // Find the highest sequence number
	    Query query = new Query();
	    query.with(Sort.by(Sort.Direction.DESC, "sequenceNumber"));
	    query.limit(1);
	    TestPOJO lastTest = mongoTemplate.findOne(query, TestPOJO.class);

	    int nextSeqNumber = (lastTest != null) ? lastTest.getSequenceNumber() + 1 : 1;
	    testPOJO.setSequenceNumber(nextSeqNumber);

	    testRepository.save(testPOJO);
	}
    
    public TestPOJO getTestBySequenceNumber(int sequenceNumber) {
        return testRepository.findBySequenceNumber(sequenceNumber);
    }
    public int getNextSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        Counter counter = mongoTemplate.findAndModify(query, update, options, Counter.class);
        return counter.getSeq();
    }
    
    public Question addQuestionToTest(int sequenceNumber, Question question) {
        TestPOJO test = testRepository.findBySequenceNumber(sequenceNumber);
//                .orElseThrow(() -> new RuntimeException("Test not found"));

        if (test.getQuestions() == null) {
            test.setQuestions(new ArrayList<>());
        }

        question.setTest(test);
        int nextId = getNextSequence("question_sequence");
        question.setId(String.valueOf(nextId));
        questionRepository.save(question);
        test.getQuestions().add(question);
        testRepository.save(test);
        return question;
    }
//	public Question addQuestionInTest(Question dto) {
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
			
//			return questionRepository.save(dto);
//		}
//		throw new EntityNotFoundException("Test Not Found");
//	}
	public List<TestPOJO> getAllTests(){
		return testRepository.findAll();
//				.stream().peek(
//				test->test.setTime(test.getQuestions().size()*test.getTime())).
//				collect(Collectors.toList());
	}
	 public List<Question> getQuestionsByTestNumber(int sequenceNumber) {
	        TestPOJO test = testRepository.findBySequenceNumber(sequenceNumber);
//	                .orElseThrow(() -> new RuntimeException("Test not found"));
	        return test.getQuestions();
	    } 
    
	
//	public Optional<TestPOJO> getAllQuestionsByTest(String id) {
//		
//				return testRepository.findById(id);
				
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
//	}
	
//	public TestResult submitTest(SubmitTestDTO request) {
//		TestPOJO testPOJO=testRepository.findById(request.getTestId()).orElseThrow(()->new EntityNotFoundException("Test Not Found"));
//		User user=userRepository.findById(request.getUserId()).orElseThrow(()->new EntityNotFoundException("User Not Found"));
//		
//		int correctAnswers=0;
//		for(QuestionResponse response:request.getResponses()) {
//			Question question=questionRepository.findById(response.getQuestionId())
//					.orElseThrow(()->new EntityNotFoundException("Question Not Found"));
//		if(question.getCorrectOption().equals(response.getSelectedOption())) {
//			correctAnswers++;
//		}
//		}
//		int totalQuestions=testPOJO.getQuestions().size();
//		double percentage=((double)correctAnswers/totalQuestions)*100;
//		 
//		TestResult testResult=new TestResult();
//		testResult.setTestPOJO(testPOJO);
//		testResult.setUser(user);
//		testResult.setTotalQuestions(totalQuestions);
//		testResult.setCorrectAnswers(correctAnswers);
//		testResult.setPercentage(percentage);
//		
//		return testResultRepository.save(testResult);
//	}

	 public TestResult submitTest(int testSequenceNumber, Map<String, String> answers) {

	       TestPOJO test = testRepository.findBySequenceNumber(testSequenceNumber);
//	        List<Question> questions = questionRepository.findByTestSequenceNumber(testSequenceNumber);
	        List<Question> questions = questionRepository.findByTestId(String.valueOf(testSequenceNumber));
	        int correct = 0;

	        for (Question q : questions) {
	            String selected = answers.get("question_" + q.getId());
	            if (selected != null && selected.equals(q.getCorrectOption())) {
	                correct++;
	            }
	        }

	        int total = questions.size();
	        double percentage = total > 0 ?((double) correct / total) * 100:0;

	        // Save result in MongoDB
	        TestResult result = new TestResult();
	        result.setTestName(test.getTitle());
//	        result.setUserName(userName);
	        result.setTotalQuestions(total);
	        result.setCorrectAnswers(correct);
	        result.setPercentage(percentage);
	        result.setTestSequenceNumber(testSequenceNumber);

	        return testResultRepository.save(result);

//	        return percentage;
	    }
	public List<TestResult> getAllTestResults(){
		return testResultRepository.findAll();
//				.stream().map(TestResult::getDto)
//				.collect(Collectors.toList());
	}
	public List<TestResultDTO> getAllTestResultsOfUser(String userId){
		return testResultRepository.findAllByUserId(userId).stream().map(TestResult::getDto)
				.collect(Collectors.toList());
	}
	
	
	}


