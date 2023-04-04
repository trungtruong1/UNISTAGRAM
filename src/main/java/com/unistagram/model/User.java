package com.unistagram.model;

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
	private int user_id;
	private String gender;
	private int age;
	private int occupation;
	private String zip_code;

	// user_id,gender,age,occupation,zip_code

	public User() {
    }

	// public User(String gender, int age, int occupation, String zip_code) {
    //     this.gender = gender;
	// 	this.age = age;
	// 	this.occupation = occupation;
	// 	this.zip_code = zip_code;
    // }

    // public User(int user_id, String gender, int age, int occupation, String zip_code) {
	// 	this.user_id = user_id;
    //     this.gender = gender;
	// 	this.age = age;
	// 	this.occupation = occupation;
	// 	this.zip_code = zip_code;
    // }

	public int getUser_id() { return this.user_id; }
	public String getGender() { return this.gender; }
	public int getAge() { return this.age; }
	public int getOccupation() { return this.occupation; }
	public String getZip_code() { return this.zip_code; }

	public void setUserId(int id) { this.user_id = id; }
}
