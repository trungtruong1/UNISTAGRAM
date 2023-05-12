package com.unistagram.matchapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.chatapp.model.Conversation;
import com.unistagram.chatapp.model.Conversation.Status;
import com.unistagram.chatapp.service.ConversationService;
import com.unistagram.userapp.model.User;
import com.unistagram.userapp.repositories.UserRepository;
import com.unistagram.userapp.service.UserService;

@Service
public class MatchingServiceImpl implements MatchingService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private MongoTemplate mongoTemplate;
    private Random rng = new Random();

    @Override
    public void joinQueue(User client){
        // Update the user status to waiting
        int user_id = client.getUser_id();
        User sample_user = userService.getUserById(user_id).get();
        sample_user.joinQueue();
        userService.updateUserInfoById(user_id, sample_user);
        boolean can_match = match(sample_user);
    }

    @Override
    public void outQueue(User client){
        // Update the user status to not waiting
        int user_id = client.getUser_id();
        User sample_user = userService.getUserById(user_id).get();
        sample_user.outQueue();
        userService.updateUserInfoById(user_id, sample_user);
    }

    @Override
    public boolean isWaiting(User client){
        return client.getIs_in_queue();
    }

    private Optional<User> getOtherRandomWaitingClient(User client){
        List<User> waitingUsers = userService.getOthersInQueue(client.getId());
        // Return null if there is no one in the queue
        if(waitingUsers.isEmpty()) {
            return null;
        }
        // Return a random user in the queue    
        return Optional.of(waitingUsers.get(rng.nextInt(waitingUsers.size())));
    }

    private void matchClients(User clientA, User clientB){
        clientA.outQueue();
        userService.updateUserInfoById(clientA.getUser_id(), clientA);
        clientB.outQueue();
        userService.updateUserInfoById(clientB.getUser_id(), clientB);
        Conversation new_conversation = new Conversation(clientA, clientB, Status.ONGOING);
        conversationService.save(new_conversation);
    }

    @Override
    public boolean match(User client) {
        Optional<User> candidate = getOtherRandomWaitingClient(client);
        if(candidate == null) {
            return false;
        }
        matchClients(client, candidate.get());
        return true;
    }

}
