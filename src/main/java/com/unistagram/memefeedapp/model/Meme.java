package com.unistagram.memefeedapp.model;

import java.util.Date;

import org.bson.types.Binary;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "meme")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class Meme {
    
    @Id
    private String id;
    private String title;
    private Binary image;
    private String author;
    @CreationTimestamp
    private Date timestamp;

    public Meme() {}
    public Meme(String title, Binary image, String author) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.timestamp = new Date();
    }
    
    public String getTitle() { return this.title; }
    public String getId() { return this.id; }
    public Binary getImage() { return this.image; }
    public String getAuthor() { return this.author; }
    public Date getTimestamp() { return this.timestamp; }

    public void setImage(Binary image) { this.image = image; }

}
