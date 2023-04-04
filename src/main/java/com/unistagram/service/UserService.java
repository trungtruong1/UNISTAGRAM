package com.unistagram.service;

import java.util.List;
import java.util.Optional;

import com.unistagram.model.User;

public interface UserService {

    String save(User user);

    List<User> getUser();

    public Optional<User> getUserById(int id);

    public Optional<User> getUserById(String id);

    public Optional<User> updateUserById(int id);

    public int getMaxId();

}
