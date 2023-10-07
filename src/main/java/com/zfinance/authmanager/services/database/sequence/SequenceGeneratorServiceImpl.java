package com.zfinance.authmanager.services.database.sequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.zfinance.authmanager.orm.database.sequences.DatabaseSequence;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

	public String generateSequence(String seqName) {
		DatabaseSequence counter = mongoOperations.findAndModify((Query.query(Criteria.where("_id").is(seqName))),
				new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
		Long seqId = !Objects.isNull(counter) ? counter.getSeq() : 1;
		return seqId.toString();
	}

}
