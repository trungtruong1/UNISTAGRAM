package com.unistagram.memefeedapp.model;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @EqualsAndHashCode(callSuper = false)
@Document(collection = "reaction")
@JsonInclude(JsonInclude.Include.NON_NULL) //include only non_null values
public class Reaction extends Meme {
    
    public Reaction() { super(); }

    public Reaction(String title, Binary image, String author) {
        super(title, image, author);
    }

}
