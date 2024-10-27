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
        String movieName = movieRatingEntity.getMovieName();
        log.info("Attempting to add a movie rating for {}", movieName);
        Optional<MovieRatingEntity> movie = movieRatingRepository.findByUserIdAndMovieName(movieRatingEntity.getUserId(), movieName);
        if (movie.isPresent()) {
            log.info("Movie rating already exists for {}", movieName);
        } else {
            movieRatingRepository.save(movieRatingEntity);
            log.info("Movie rating for {} has been added", movieName);
        }
    }

    public void updateMovieRating(MovieRatingEntity movieRatingEntity) {
        String movieName = movieRatingEntity.getMovieName();
        log.info("Attempting to update a movie rating for {}", movieName);
        Optional<MovieRatingEntity> movie = movieRatingRepository.findByUserIdAndMovieName(movieRatingEntity.getUserId(), movieName);
        if (movie.isPresent()) {
            movieRatingEntity.setId(movie.get().getId());
            movieRatingRepository.save(movieRatingEntity);
            log.info("Movie rating for {} has been updated", movieName);
        } else {
            log.info("Movie rating for {} does not exists", movieName);
        }
    }
}
