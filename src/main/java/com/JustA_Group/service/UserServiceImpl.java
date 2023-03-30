package com.JustA_Group.service;

import com.JustA_Group.model.User;
import com.JustA_Group.repositories.UserRepository;

import org.springframework.stereotype.Service;

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
    public List<User> getUser(String firstName){
        return userRepository.findByFirstName(firstName);
    }
}
