package com.unistagram.service;

import java.util.List;
import java.util.Optional;

import com.unistagram.model.User;

public interface UserService {

    String save(User user);

    List<User> getUser();

    public List<User> getUserById(int id);

    public Optional<User> getUserById(String id);

    public int getMaxId();

}
