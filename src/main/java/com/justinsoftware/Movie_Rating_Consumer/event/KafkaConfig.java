package com.justinsoftware.Movie_Rating_Consumer.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaConfig {

    public static final String CREATE_MOVIE_TOPIC = "movieRatingProducer-json-createMovieRating";
    public static final String UPDATE_MOVIE_TOPIC = "movieRatingProducer-json-updateMovieRating";

}
