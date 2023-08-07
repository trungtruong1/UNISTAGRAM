package com.unistagram.memefeedapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.repositories.ReactionRepository;

@Service
public class ReactionServiceImpl extends MemeServiceImpl implements ReactionService {
    
    @Autowired
    private ReactionRepository memeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(String title, MultipartFile file, String author) throws IOException {
        Reaction new_reaction = new Reaction(title, new Binary(BsonBinarySubType.BINARY, file.getBytes()), author);
        memeRepository.save(new_reaction);
        return new_reaction.getId();
    }

    @Override
    public Optional<Reaction> getReactionById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Reaction reaction = mongoTemplate.findOne(query, Reaction.class);
        if(reaction == null) {
            return Optional.empty();
        }
        return Optional.of(reaction);
    }

    @Override
    public List<Reaction> getReactionByUsername(String username) {
        Query query = new Query(Criteria.where("author").is(username));
        return mongoTemplate.find(query, Reaction.class);
    }
}
