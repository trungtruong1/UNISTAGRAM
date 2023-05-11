package com.unistagram.chatapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unistagram.userapp.model.User;

import lombok.Data;

@Data
@Document(collection = "message")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class Message {
    
    @Id
    private String id;
    
    @DocumentReference(lazy = true)
    private Conversation conversation;

    @DocumentReference(lazy = true)
    private User sender;

    private String content;

}
