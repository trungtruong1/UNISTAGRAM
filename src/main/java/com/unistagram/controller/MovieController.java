package com.unistagram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unistagram.model.Movie;
import com.unistagram.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // @PostMapping
    // public String save(@RequestBody Movie movie) {
    //     return movieService.save(movie);
    // }

    @GetMapping("/{movie}")
    @ResponseBody
    public Movie getMovie(@PathVariable("movie") int movie) {
        return movieService.getMovieById(movie);
    }
}
