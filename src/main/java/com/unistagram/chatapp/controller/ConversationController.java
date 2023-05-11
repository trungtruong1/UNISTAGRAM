package com.unistagram.chatapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.matchapp.model.MatchingMessage;
import com.unistagram.matchapp.model.MatchingRecieve;
import com.unistagram.matchapp.service.MatchingService;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.service.UserService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the user");
    }

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User id does not exist!");
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body("Parameter is not a number!");
    }

    @GetMapping("/")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversations(@PathVariable("id") String id) {
        Optional<Conversation> conversation = conversationService.getConversationById(id);
        if(conversation == null) {
            throw new ParameterErrorNumberException("Conversation id does not exist!");
        }
        return ResponseEntity.ok(conversation.get());
    }

}
