package com.justinsoftware.Movie_Rating_Consumer.repository;

import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRatingEntity, Long> {

    Optional<MovieRatingEntity> findByUserIdAndMovieName(Integer userId, String movieName);
}
