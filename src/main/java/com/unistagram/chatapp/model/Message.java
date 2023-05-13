package com.unistagram.chatapp.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
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
    
    private String conversation;

    private String sender;

    private String receiver;

    private String content;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public Message(String conversation, String sender, String receiver, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
