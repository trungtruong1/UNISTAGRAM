package com.unistagram.chatapp.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

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
    private String client1;
    private String client2;
    private Status status;

    public Conversation() {}

    public Conversation(String client1, String client2, Status status) {
        this.client1 = client1;
        this.client2 = client2;
        this.status = status;
    }

    public String getId() { return this.id; }

    public String getClient1() { return this.client1; }

    public String getClient2() { return this.client2; }

    public Status getStatus() { return this.status; }

}
