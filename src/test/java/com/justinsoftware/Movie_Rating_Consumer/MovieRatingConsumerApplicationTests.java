package com.justinsoftware.Movie_Rating_Consumer;

import com.justinsoftware.Movie_Rating_Consumer.controller.MovieRatingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MovieRatingConsumerApplicationTests {

    @Autowired
    private MovieRatingController movieRatingController;

    @Test
    void contextLoads() {
        assertThat(movieRatingController).isNotNull();
    }

}
