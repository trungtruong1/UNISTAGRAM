package com.JustA_Group.service;

import java.util.List;

import com.JustA_Group.model.User;;

public interface UserService {

    String save(User user);

    List<User> getUser(String firstName);

}
