package com.justinsoftware.Movie_Rating_Consumer.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.justinsoftware.Movie_Rating_Consumer.event.KafkaConfig.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class Consumer {

    private final MovieRatingService movieRatingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = CREATE_MOVIE_TOPIC)
    public void processCreateMovie(String content) throws JsonProcessingException {
        MovieRatingEntity movieRatingEntity = getMovieRatingMessage(content);
        movieRatingService.addMovieRating(movieRatingEntity);
    }

    @KafkaListener(topics = UPDATE_MOVIE_TOPIC)
    public void processUpdateMovie(String content) throws JsonProcessingException {
        MovieRatingEntity movieRatingEntity = getMovieRatingMessage(content);
        movieRatingService.updateMovieRating(movieRatingEntity);
    }

    private MovieRatingEntity getMovieRatingMessage(String content) throws JsonProcessingException {
        return objectMapper.readValue(content, MovieRatingEntity.class);
    }
}
