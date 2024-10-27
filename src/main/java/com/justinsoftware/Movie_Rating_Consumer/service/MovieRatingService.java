package com.justinsoftware.Movie_Rating_Consumer.service;

import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.repository.MovieRatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieRatingService {

    private final MovieRatingRepository movieRatingRepository;

    public void addMovieRating(MovieRatingEntity movieRatingEntity) {
        Integer userId = movieRatingEntity.getUserId();
        String movieName = movieRatingEntity.getMovieName();
        log.info("Attempting to add a movie rating for {} for user {}", movieName, userId);
        Optional<MovieRatingEntity> movie = movieRatingRepository.findByUserIdAndMovieName(userId, movieName);
        if (movie.isPresent()) {
            log.info("Movie rating already exists for {} for user {}", movieName, userId);
        } else {
            movieRatingRepository.save(movieRatingEntity);
            log.info("Movie rating for {} has been added for user {}", movieName, userId);
        }
    }

    public void updateMovieRating(MovieRatingEntity movieRatingEntity) {
        Integer userId = movieRatingEntity.getUserId();
        String movieName = movieRatingEntity.getMovieName();
        log.info("Attempting to update a movie rating for {} for user {}", movieName, userId);
        Optional<MovieRatingEntity> movie = movieRatingRepository.findByUserIdAndMovieName(userId, movieName);
        if (movie.isPresent()) {
            movieRatingEntity.setId(movie.get().getId());
            movieRatingRepository.save(movieRatingEntity);
            log.info("Movie rating for {} has been updated for user {}", movieName, userId);
        } else {
            log.info("Movie rating for {} does not exists for user {}", movieName, userId);
        }
    }
}
