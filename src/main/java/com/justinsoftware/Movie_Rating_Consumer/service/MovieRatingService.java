package com.justinsoftware.Movie_Rating_Consumer.service;

import com.justinsoftware.Movie_Rating_Consumer.dto.MovieRatingDTO;
import com.justinsoftware.Movie_Rating_Consumer.state.MovieRatingState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieRatingService {

    public void addMovieRating(MovieRatingDTO movieRatingDTO) {
        String movieName = movieRatingDTO.movieName();
        log.info("Attempting to add a movie rating for {}", movieName);
        MovieRatingState.addMovieRating(movieRatingDTO);
        log.info("Movie rating for {} has been added", movieName);
    }

    public void updateMovieRating(MovieRatingDTO movieRatingDTO) {
        String movieName = movieRatingDTO.movieName();
        log.info("Attempting to update a movie rating for {}", movieName);
        if (MovieRatingState.updateMovieRating(movieRatingDTO) == null) {
            log.info("Movie rating for {} does not exists", movieName);
        } else {
            log.info("Movie rating for {} has been updated", movieName);
        }
    }
}
