package com.example.quizapplication.rep;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.quizapplication.entity.Question;
import com.example.quizapplication.repository.QuestionRepository;

@DataMongoTest
public class QuestionRepositoryTest {

	@Autowired
    private QuestionRepository questionRepository;

    private Question q1, q2, q3;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
        q1 = new Question();
        q1.setTestId("T1");
        q1.setTestSequenceNumber("1");
        q1.setQuestionText("What is Java?");

        q2 = new Question();
        q2.setTestId("T1");
        q2.setTestSequenceNumber("2");
        q2.setQuestionText("What is Spring Boot?");

        q3 = new Question();
        q3.setTestId("T2");
        q3.setTestSequenceNumber("1");
        q3.setQuestionText("Explain MongoDB.");

        questionRepository.saveAll(List.of(q1, q2, q3));
    }

    @Test
    @DisplayName("Find questions by testId")
    void testFindByTestId() {
        List<Question> questions = questionRepository.findByTestId("T1");

        assertThat(questions).isNotEmpty();
        assertThat(questions).hasSize(2);
        assertThat(questions).allMatch(q -> q.getTestId().equals("T1"));
    }

    @Test
    @DisplayName("Find questions by testSequenceNumber")
    void testFindByTestSequenceNumber() {
        List<Question> questions = questionRepository.findByTestSequenceNumber("1");

        assertThat(questions).isNotEmpty();
        assertThat(questions).hasSize(2); // q1 and q3
        assertThat(questions).anyMatch(q -> q.getQuestionText().equals("What is Java?"));
        assertThat(questions).anyMatch(q -> q.getQuestionText().equals("Explain MongoDB."));
    }

    @Test
    @DisplayName("Return empty list for non-existent testId")
    void testFindByTestId_NoResults() {
        List<Question> questions = questionRepository.findByTestId("T99");
        assertThat(questions).isEmpty();
    }
}
