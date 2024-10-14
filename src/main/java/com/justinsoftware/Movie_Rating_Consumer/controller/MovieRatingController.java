package com.justinsoftware.Movie_Rating_Consumer.controller;

import com.justinsoftware.Movie_Rating_Consumer.dto.MovieRatingDTO;
import com.justinsoftware.Movie_Rating_Consumer.state.MovieRatingState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/movie/rating")
public class MovieRatingController {

    @GetMapping("/")
    public ResponseEntity<Object> getMovieRatings() {
        Map<String, MovieRatingDTO> movieRatings = MovieRatingState.getMovieRatings();
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRatings);
    }
}
