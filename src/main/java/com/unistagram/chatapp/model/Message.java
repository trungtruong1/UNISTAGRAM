package com.unistagram.chatapp.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

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
    private Date timestamp;

    public Message(String conversation, String sender, String receiver, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = new Date();
    }
}
