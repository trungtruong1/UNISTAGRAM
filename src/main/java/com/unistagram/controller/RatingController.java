package com.unistagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.model.Movie;
import com.unistagram.model.Rating;
import com.unistagram.model.Result;
import com.unistagram.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public String save(@RequestBody Rating rating) {
        return ratingService.save(rating);
    }

    @GetMapping("/{rating}")
    @ResponseBody
    public List<Object> getRating(@PathVariable("rating") int rating) {
        Result<List<Object>> result = ratingService.getMovieRatingGTE(rating);
        if(result.isOK()) {
            return result.getResult();
        }
        // TODO: Need to handle error.
        return result.getResult();
    }
}
