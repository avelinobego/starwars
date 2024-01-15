package br.com.americanas.digital.starwars.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.americanas.digital.starwars.entities.DatabaseSequence;

@Service
public class SequenceService {

    private final MongoOperations mongoOperations;

    public SequenceService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {

        // https://www.baeldung.com/spring-boot-mongodb-auto-generated-field
        DatabaseSequence counter = mongoOperations
                .findAndModify(query(where("_id")
                        .is(seqName)),
                        new Update()
                                .inc("seq", 1),
                        options()
                                .returnNew(true)
                                .upsert(true),
                        DatabaseSequence.class);

        return !Objects.isNull(counter) ? counter.seq() : 1;
    }

}
