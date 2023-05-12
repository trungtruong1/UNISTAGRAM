package com.unistagram.userapp.service;

import java.util.List;
import java.util.Optional;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.userapp.model.User;

public interface UserService {

    String save(User user);

    List<User> getUser();

    public List<User> getOthersInQueue(String client_id);

    public Optional<User> getUserById(int id);

    public Optional<User> getUserById(String id);

    public UpdateResult updateUserInfoById(int id, User updated_user);

    public int getMaxId();

}
