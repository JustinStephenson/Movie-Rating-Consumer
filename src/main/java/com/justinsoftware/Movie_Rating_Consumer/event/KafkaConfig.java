package com.justinsoftware.Movie_Rating_Consumer.event;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public static final String CREATE_MOVIE_TOPIC = "movieRatingProducer-json-createMovieRating";
    public static final String UPDATE_MOVIE_TOPIC = "movieRatingProducer-json-updateMovieRating";

}
