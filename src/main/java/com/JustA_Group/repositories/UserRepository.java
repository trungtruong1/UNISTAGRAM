package com.JustA_Group.repositories;
import com.JustA_Group.model.User;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByFirstName(String firstName);

}
