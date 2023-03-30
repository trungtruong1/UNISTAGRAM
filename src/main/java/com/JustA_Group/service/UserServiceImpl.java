package com.JustA_Group.service;

import com.JustA_Group.model.User;
import com.JustA_Group.repositories.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;;;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public String save(User user){
        return userRepository.save(user).getId();
    }
}
