package com.unistagram.memefeedapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unistagram.memefeedapp.model.Meme;
import com.unistagram.memefeedapp.repositories.MemeRepository;

@Service
@Primary
public class MemeServiceImpl implements MemeService {
    
    @Autowired
    private MemeRepository memeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Meme> getAllMeme() {
        return mongoTemplate.findAll(Meme.class);
    }

    @Override
    public String save(String title, MultipartFile file, String author) throws IOException {
        Meme new_meme = new Meme(title, new Binary(BsonBinarySubType.BINARY, file.getBytes()), author);
        memeRepository.save(new_meme);
        return new_meme.getId();
    }

    @Override
    public Optional<Meme> getMemeById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Meme meme = mongoTemplate.findOne(query, Meme.class);
        if(meme == null) {
            return Optional.empty();
        }
        return Optional.of(meme);
    }
}
