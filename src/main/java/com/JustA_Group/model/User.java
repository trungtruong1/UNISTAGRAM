package com.JustA_Group.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data 
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class User {

	@Id
	private String id;
	private String firstName;
	private String lastName;

	public User() {
    }

    public User(String name, int age) {
        this.firstName = name;
    }
}
