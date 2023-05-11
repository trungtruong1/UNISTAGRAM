package com.unistagram.userapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class User {

	@Id
	private String _id;
	private int user_id;
	private String username;
	private String password;
	private String email;
	private int age;
	private String gender;
	private String nationality;
	private boolean is_in_queue;
	private String[] music;
	private String[] film;
	private String[] activity;

	// user_id,gender,age,occupation,zip_code

	public User() {}

	public String getId() { return this._id; }
	public int getUser_id() { return this.user_id; }
	public String getUsername() { return this.username; }
	public String getEmail() { return this.email; }
	public int getAge() { return this.age; }
	public String getGender() { return this.gender; }
	public String getNationality() { return this.nationality; }
	public boolean getIs_in_queue() { return this.is_in_queue; }
	public String[] getMusic() { return this.music; }
	public String[] getFilm() { return this.film; }
	public String[] getActivity() { return this.activity; }

	public void setUserId(int id) { this.user_id = id; }

	public void joinQueue() { this.is_in_queue = true; }
	public void outQueue() { this.is_in_queue = false; }
}
