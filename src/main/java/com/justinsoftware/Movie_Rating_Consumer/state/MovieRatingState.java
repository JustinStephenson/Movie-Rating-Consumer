package com.justinsoftware.Movie_Rating_Consumer.state;

import com.justinsoftware.Movie_Rating_Consumer.dto.MovieRatingDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MovieRatingState {

    private static final Map<String, MovieRatingDTO> state = new HashMap<>();

    public static Map<String, MovieRatingDTO> getMovieRatings() {
        return state;
    }

    public static void addMovieRating(MovieRatingDTO movieRatingDTO) {
        state.putIfAbsent(movieRatingDTO.movieName(), movieRatingDTO);
    }

    public static MovieRatingDTO updateMovieRating(MovieRatingDTO movieRatingDTO) {
        return state.replace(movieRatingDTO.movieName(), movieRatingDTO);
    }
}
