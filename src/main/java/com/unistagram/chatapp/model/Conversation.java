package com.unistagram.chatapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unistagram.userapp.model.User;

import lombok.Data;

@Data
@Document(collection = "conversation")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class Conversation {

    public enum Status {
        ONGOING,
        TERMINATED
    }
    
    @Id
    private String id;
    @DocumentReference
    private User client1;
    @DocumentReference
    private User client2;
    private Status status;

    public Conversation() {}

    public Conversation(User client1, User client2, Status status) {
        this.client1 = client1;
        this.client2 = client2;
        this.status = status;
    }

    public String getId() { return this.id; }

    public User getClient1() { return this.client1; }

    public User getClient2() { return this.client2; }

    public Status getStatus() { return this.status; }

}
