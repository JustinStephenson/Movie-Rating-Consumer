package com.justinsoftware.Movie_Rating_Consumer.integration;

import com.justinsoftware.Movie_Rating_Consumer.entity.MovieRatingEntity;
import com.justinsoftware.Movie_Rating_Consumer.event.KafkaConfig;
import com.justinsoftware.Movie_Rating_Consumer.repository.MovieRatingRepository;
import com.justinsoftware.Movie_Rating_Consumer.util.BaseTestContainer;
import com.justinsoftware.Movie_Rating_Consumer.util.TestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class AddMovieRatingTest extends BaseTestContainer {

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @ParameterizedTest
    @MethodSource("addMovieRatingMessageArguments")
    void testAddMoveRatingMessage(String jsonMoviePath) {
        // Given
        MovieRatingEntity givenMovie = TestUtils.createObjectFromJsonResource(jsonMoviePath, MovieRatingEntity.class);
        assertThat(givenMovie).isNotNull();

        String message = TestUtils.stringifyJson(givenMovie);

        // When
        createMessage(KafkaConfig.CREATE_MOVIE_TOPIC, message);

        // Then
        Optional<MovieRatingEntity> actualMovie = movieRatingRepository.findByUserIdAndMovieName(givenMovie.getUserId(), givenMovie.getMovieName());
        if (actualMovie.isPresent()) {
            assertThat(actualMovie.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(givenMovie);
        } else {
            fail("No movie found");
        }
    }

    private static Stream<Arguments> addMovieRatingMessageArguments() {
        return Stream.of(
                Arguments.of("data/add/jungle-book.json"),
                Arguments.of("data/add/lion-king.json"),
                Arguments.of("data/add/shrek.json")
        );
    }
}
