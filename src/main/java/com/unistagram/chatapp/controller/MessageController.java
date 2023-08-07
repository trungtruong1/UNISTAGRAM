package com.unistagram.chatapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.model.Message;
import com.unistagram.chatapp.model.MessageReceive;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.chatapp.service.MessageService;
import com.unistagram.userapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    public static class SendMessageInfo {
        String conversation;
        String sender;
        String content;
        boolean terminated;

        public SendMessageInfo() {}

        public SendMessageInfo(String conversation, String sender, String content) {
            this.conversation = conversation;
            this.sender = sender;
            this.content = content;
            this.terminated = false;
        }

        public SendMessageInfo(String conversation, String sender, String content, boolean terminated) {
            this.conversation = conversation;
            this.sender = sender;
            this.content = content;
            this.terminated = terminated;
        }
    
        public String getConversation() { return this.conversation; }
    
        public String getSender() { return this.sender; }
    
        public String getContent() { return this.content; }

        public boolean getTerminated() { return this.terminated; }
    }

    @ExceptionHandler(ObjectIdException.class)
    public ResponseEntity<String> handleObjectIdException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something wrong when saving the message");
    }

    @ExceptionHandler(ParameterErrorNumberException.class)
    public ResponseEntity<String> handleParameterErrorNumber(ParameterErrorNumberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(ParameterErrorStringException.class)
    public ResponseEntity<String> handleParameterErrorString(ParameterErrorStringException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body(ex.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Message>> getMessagesInConversation(@PathVariable("id") String id) {
        List<Message> conversation = messageService.getAllMessageInConversation(id);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessageToConversation(@RequestBody SendMessageInfo message) {
        Optional<Conversation> conversation = conversationService.getConversationById(message.getConversation());
        if(conversation.isEmpty()) {
            throw new ParameterErrorNumberException("Conversation does not exist!");
        }
        if(!conversation.get().getStatus().equals(Conversation.Status.ONGOING)){
            throw new ParameterErrorStringException("Conversation has been terminated!");
        }
        if(!message.sender.equals(conversation.get().getClient1()) && !message.sender.equals(conversation.get().getClient2())) {
            throw new ParameterErrorStringException("Sender is not in this conversation!");
        }
        String receiver = "";
        if(message.sender.equals(conversation.get().getClient1())) {
            receiver = conversation.get().getClient2();
        }
        if(message.sender.equals(conversation.get().getClient2())) {
            receiver = conversation.get().getClient1();
        }
        String message_id = messageService.saveNewMessage(message.getConversation(), message.getSender(), receiver, message.getContent());
        Optional<Message> new_message = messageService.getMessageById(message_id);
        return ResponseEntity.ok(new_message.get());
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload SendMessageInfo message){
        Optional<Conversation> conversation = conversationService.getConversationById(message.getConversation());
        if(conversation.isEmpty()) {
            throw new ParameterErrorNumberException("Conversation does not exist!");
        }
        if(!conversation.get().getStatus().equals(Conversation.Status.ONGOING)){
            messagingTemplate.convertAndSend("/sub/chat/room/" + message.getConversation(), new SendMessageInfo(null, null, null, true));
            throw new ParameterErrorStringException("Conversation has been terminated!");
        }
        if(!message.sender.equals(conversation.get().getClient1()) && !message.sender.equals(conversation.get().getClient2())) {
            throw new ParameterErrorStringException("Sender is not in this conversation!");
        }
        String receiver = "";
        if(message.sender.equals(conversation.get().getClient1())) {
            receiver = conversation.get().getClient2();
        }
        if(message.sender.equals(conversation.get().getClient2())) {
            receiver = conversation.get().getClient1();
        }
        messageService.saveNewMessage(message.getConversation(), message.getSender(), receiver, message.getContent());
        
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getConversation(), message);
    }

}
