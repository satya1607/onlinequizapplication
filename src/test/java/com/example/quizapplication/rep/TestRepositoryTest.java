package com.example.quizapplication.rep;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.quizapplication.entity.TestPOJO;
import com.example.quizapplication.repository.TestRepository;

@DataMongoTest
public class TestRepositoryTest {
	 @Autowired
	    private TestRepository testRepository;

	    private TestPOJO test1;
	    private TestPOJO test2;

	    @BeforeEach
	    void setUp() {
	        testRepository.deleteAll();

	        // Using no-args constructor + setters to avoid constructor issues
	        test1 = new TestPOJO();
	        test1.setTitle("Java Basics");
	        test1.setSequenceNumber(1);

	        test2 = new TestPOJO();
	        test2.setTitle("Spring Boot");
	        test2.setSequenceNumber(2);

	        testRepository.save(test1);
	        testRepository.save(test2);
	    }

	    @Test
	    @DisplayName("Find test by sequenceNumber")
	    void testFindBySequenceNumber() {
	        TestPOJO found = testRepository.findBySequenceNumber(1);

	        assertThat(found).isNotNull();
	        assertThat(found.getTitle()).isEqualTo("Java Basics");
	        assertThat(found.getSequenceNumber()).isEqualTo(1);
	    }

	    @Test
	    @DisplayName("Return null for non-existent sequenceNumber")
	    void testFindBySequenceNumber_NotFound() {
	        TestPOJO found = testRepository.findBySequenceNumber(99);
	        assertThat(found).isNull();
	    }
}
