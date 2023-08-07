package com.unistagram.memefeedapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.service.MemeService;
import com.unistagram.memefeedapp.service.ReactionService;
import com.unistagram.userapp.exception.ObjectIdException;
import com.unistagram.userapp.exception.ParameterErrorNumberException;
import com.unistagram.userapp.exception.ParameterErrorStringException;
import com.unistagram.userapp.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Reaction> getMemeById(@PathVariable("id") String id) {
        Optional<Reaction> reaction = reactionService.getReactionById(id);
        if(reaction.isEmpty()) {
            throw new ParameterErrorStringException("The reaction id does not exist!");
        }
        return ResponseEntity.ok(reaction.get());
    }

    @PostMapping
    public ResponseEntity<Reaction> saveReaction(@RequestParam String title, @RequestParam MultipartFile image, @RequestParam String author) throws IOException {
        String reaction_id = reactionService.save(title, image, author);
        return ResponseEntity.ok(reactionService.getReactionById(reaction_id).get());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Reaction>> getMemeByUser(@PathVariable("username") String username) {
        List<Reaction> reaction = reactionService.getReactionByUsername(username);
        return ResponseEntity.ok(reaction);
    }
}
