package com.justinsoftware.Movie_Rating_Consumer.controller;

import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.repository.MovieRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/movie/rating")
public class MovieRatingController {

    private final MovieRatingRepository movieRatingRepository;

    @GetMapping("/")
    public ResponseEntity<Object> getMovieRatings() {
        List<MovieRatingEntity> movieRatings = movieRatingRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(movieRatings);
    }
}
