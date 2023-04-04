package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.model.User;
import com.unistagram.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(User user){
        user.setUserId(getMaxId() + 1);
        return userRepository.save(user).getId();
    }

    @Override
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        Query query = new Query(Criteria.where("user_id").is(id));
        User user = mongoTemplate.findOne(query, User.class);
        if(user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UpdateResult updateUserById(int id, User updated_user) {
        Query query = new Query(Criteria.where("user_id").is(id));
        Update update = new Update()
        .set("gender", updated_user.getGender())
        .set("age", updated_user.getAge())
        .set("occupation", updated_user.getOccupation())
        .set("zip_code", updated_user.getZipCode());
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        return result;
    }

    @Override
    public int getMaxId() {
        Query query = new Query();
        query.limit(1).with(Sort.by(Sort.Direction.DESC, "user_id"));
        return mongoTemplate.find(query, User.class).get(0).getUser_id();
    }
}
