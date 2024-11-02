package com.justinsoftware.Movie_Rating_Consumer.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import static com.justinsoftware.Movie_Rating_Consumer.event.KafkaConfig.*;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Consumer {

    private final MovieRatingService movieRatingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {CREATE_MOVIE_TOPIC, UPDATE_MOVIE_TOPIC})
    public void listenToMovieMessage(String content, @Header(name = KafkaHeaders.RECEIVED_TOPIC) String topic) {
        MovieRatingEntity movieRatingEntity = handleMovieRatingMessage(content);
        if (movieRatingEntity != null) {
            switch (topic) {
                case CREATE_MOVIE_TOPIC -> movieRatingService.addMovieRating(movieRatingEntity);
                case UPDATE_MOVIE_TOPIC -> movieRatingService.updateMovieRating(movieRatingEntity);
            }
            log.info("Successfully processed message");
        } else {
            log.error("Error while processing message");
        }
    }

    private MovieRatingEntity handleMovieRatingMessage(String content) {
        try {
            log.info("Handling message");
            return objectMapper.readValue(content, MovieRatingEntity.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
