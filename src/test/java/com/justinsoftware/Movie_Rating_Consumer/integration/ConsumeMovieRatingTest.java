package com.justinsoftware.Movie_Rating_Consumer.integration;

import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.repository.MovieRatingRepository;
import com.justinsoftware.Movie_Rating_Consumer.util.BaseTestContainer;
import com.justinsoftware.Movie_Rating_Consumer.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class ConsumeMovieRatingTest extends BaseTestContainer {

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Test
    void name() {
        // Given
        MovieRatingEntity givenMovie = TestUtils.createObjectFromJsonResource("data/lion-king.json", MovieRatingEntity.class);
        givenMovie.setId(1L); // automatically set in Entity class
        String message = TestUtils.stringifyJson(givenMovie);

        // When
        createMessage(TestUtils.KAFKA_CREATE_TOPIC, message);

        // Then
        Optional<MovieRatingEntity> actualMovie = movieRatingRepository.findByUserIdAndMovieName(givenMovie.getUserId(), givenMovie.getMovieName());
        if (actualMovie.isPresent()) {
            assertThat(actualMovie.get()).usingRecursiveComparison().isEqualTo(givenMovie);
        } else {
            fail("No movie found");
        }
    }
}
