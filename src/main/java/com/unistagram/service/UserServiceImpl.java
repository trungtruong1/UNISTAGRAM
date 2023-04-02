package com.unistagram.service;

import org.springframework.stereotype.Service;

import com.unistagram.model.User;
import com.unistagram.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;;;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public String save(User user){
        return userRepository.save(user).getId();
    }

    @Override
    public List<User> getUser(){
        return userRepository.findAll();
    }
}
