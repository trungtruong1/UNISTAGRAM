package com.unistagram.service;

import java.util.List;

import com.unistagram.model.User;

public interface UserService {

    String save(User user);

    List<User> getUser();

}
