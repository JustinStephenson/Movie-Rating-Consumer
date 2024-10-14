package com.justinsoftware.Movie_Rating_Consumer.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justinsoftware.Movie_Rating_Consumer.dto.MovieRatingDTO;
import com.justinsoftware.Movie_Rating_Consumer.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.justinsoftware.Movie_Rating_Consumer.event.KafkaConfig.*;


@RequiredArgsConstructor
@Component
public class Consumer {

    private final MovieRatingService movieRatingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = CREATE_MOVIE_TOPIC)
    public void processCreateMovie(String content) throws JsonProcessingException {
        MovieRatingDTO movieRatingDTO = objectMapper.readValue(content, MovieRatingDTO.class);
        movieRatingService.addMovieRating(movieRatingDTO);
    }

    @KafkaListener(topics = UPDATE_MOVIE_TOPIC)
    public void processUpdateMovie(String content) throws JsonProcessingException {
        MovieRatingDTO movieRatingDTO = objectMapper.readValue(content, MovieRatingDTO.class);
        movieRatingService.updateMovieRating(movieRatingDTO);
    }
}
