package com.unistagram.userapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(User user){
        user.setUserId(getMaxId() + 1);
        return userRepository.save(user).getId().toString();
    }

    @Override
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @Override
    public List<User> getOthersInQueue(String client_id) {
        Query query = new Query(Criteria.where("id")
                                .ne(client_id)
                                .and("is_in_queue").is(true)
        );
        return mongoTemplate.find(query, User.class);
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
    public UpdateResult updateUserInfoById(int id, User updated_user) {
        Query query = new Query(Criteria.where("user_id").is(id));
        Update update = new Update()
        .set("username", updated_user.getUsername())
        .set("gender", updated_user.getGender())
        .set("email", updated_user.getEmail())
        .set("nationality", updated_user.getNationality())
        .set("is_in_queue", updated_user.getIs_in_queue())
        .set("music", updated_user.getMusic())
        .set("film", updated_user.getFilm())
        .set("activity", updated_user.getActivity())
        .set("age", updated_user.getAge());
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
