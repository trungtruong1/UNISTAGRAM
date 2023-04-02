package com.unistagram.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection = "rating")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class Rating {

	@Id
	private String id;
	private int user_id;
	private int movie_id;
	private int rating;
	private int timestamp;

	public Rating() {
    }

    public Rating(int user_id, int movie_id, int rating, int timestamp) {
        this.user_id = user_id;
		this.movie_id = movie_id;
		this.rating = rating;
		this.timestamp = timestamp;
    }
}
