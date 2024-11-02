package com.justinsoftware.Movie_Rating_Consumer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String KAFKA_CREATE_TOPIC = "movieRatingProducer-json-createMovieRating";

    public static String stringifyJson(Object jsonObject) {
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            fail();
            return null;
        }
    }

    public static <T> T createObjectFromJsonResource(String path, Class<T> clazz) {
        try {
            File jsonFile = new File("src/test/resources/" + path);
            return objectMapper.readValue(new FileInputStream(jsonFile), clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            fail();
            return null;
        }
    }
}
