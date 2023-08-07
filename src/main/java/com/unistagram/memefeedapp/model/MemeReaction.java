package com.unistagram.memefeedapp.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "meme_reaction")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class MemeReaction {
    
    @Id
    private String id;
    private String meme_id;
    private String reaction_id;
    private String user_id;
    @CreationTimestamp
    private Date timestamp;

    public MemeReaction() {} 
    public MemeReaction(String meme_id, String reaction_id, String user) {
        this.meme_id = meme_id;
        this.reaction_id = reaction_id;
        this.user_id = user;
    }
}
