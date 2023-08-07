package com.unistagram.memefeedapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.memefeedapp.model.MemeReaction;
import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.service.MemeReactionService;
import com.unistagram.userapp.service.UserService;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/meme_reactions")
public class MemeReactionController {
    
    @Autowired
    private MemeReactionService memeReactionService;
    @Autowired
    private UserService userService;

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

    @GetMapping("")
    public ResponseEntity<MemeReaction> getReactionsOfUserInMeme(@RequestParam("user_id") String user_id, @RequestParam("meme_id") String meme_id) {
        Optional<MemeReaction> res = memeReactionService.getMemeReactionByMemeAndUser(meme_id, user_id);
        if(res.isEmpty()) {
            throw new ParameterErrorStringException("This user hasnot reacted to this meme");
        }
        return ResponseEntity.ok(res.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Integer>> getReactionsInMeme(@PathVariable("id") String id) {
        HashMap<String, Integer> reaction = memeReactionService.getMemeReactionsByMemeId(id);
        return ResponseEntity.ok(reaction);
    }

    @PostMapping("/add")
    public ResponseEntity<MemeReaction> addReaction(@RequestParam String meme_id, @RequestParam String reaction_id, @RequestParam String user_id){
        if(memeReactionService.getMemeReactionByMemeAndUser(meme_id, user_id).isPresent()) {
            throw new ParameterErrorStringException("This user has already reacted to this meme!");
        }

        memeReactionService.save(meme_id, reaction_id, user_id);
        return ResponseEntity.ok(memeReactionService.getMemeReactionById(meme_id, reaction_id, user_id).get());
    }

    @DeleteMapping("/del")
    public ResponseEntity<Boolean> removeReaction(@RequestParam String meme_id, @RequestParam String reaction_id, @RequestParam String user_id) {
        Optional<MemeReaction> meme_reaction = memeReactionService.getMemeReactionByMemeAndUser(meme_id, user_id);
        if(meme_reaction.isEmpty()) {
            throw new ParameterErrorStringException("This is not your reaction!");
        }
        return ResponseEntity.ok(memeReactionService.removeReaction(meme_reaction.get().getId()));
    }

}
