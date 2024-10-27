package com.justinsoftware.Movie_Rating_Consumer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_rating")
public class MovieRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_rating_id_seq")
    @SequenceGenerator(name = "movie_rating_id_seq", sequenceName = "movie_rating_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "message")
    private String message;

    protected MovieRatingEntity() {
    }

    public MovieRatingEntity(Integer userId, String movieName, Integer rating, String message) {
        this.userId = userId;
        this.movieName = movieName;
        this.rating = rating;
        this.message = message;
    }
}
