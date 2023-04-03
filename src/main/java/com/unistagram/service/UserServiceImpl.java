package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.unistagram.model.User;
import com.unistagram.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
    public List<User> getUserById(int id) {
        Query query = new Query(Criteria.where("user_id").is(id));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public int getMaxId() {
        Query query = new Query();
        query.limit(1).with(Sort.by(Sort.Direction.DESC, "user_id"));
        return mongoTemplate.find(query, User.class).get(0).getUser_id();
    }
}
