package com.example.quizapplication.config;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.quizapplication.entity.DatabaseSequence;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;

@Service
public class SequenceGeneratorService {

	 @Autowired
	    private MongoOperations mongoOperations;

	    public int generateSequence(String seqName) {
	        DatabaseSequence counter = mongoOperations.findAndModify(
	            Query.query(Criteria.where("_id").is(seqName)),
	            new Update().inc("seq", 1),
	            FindAndModifyOptions.options().returnNew(true).upsert(true),
	            DatabaseSequence.class
	        );
	        return !Objects.isNull(counter) ? counter.getSeq() : 1;
	    }
}
