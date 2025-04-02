package com.example.res_api_assignment2.repositories;

import com.example.res_api_assignment2.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    // Search by name (case-insensitive, partial match)
    List<Movie> findByNameContainingIgnoreCase(String keyword);

    // Featured content (movies or TV shows)
    List<Movie> findByIsFeatured(boolean isFeatured); // All featured content

    // All movies and TV shows
    List<Movie> findByIsMovie(boolean isMovie);    // All movies

    // Combined search with filters
    List<Movie> findByIsFeaturedAndIsMovie(boolean isFeatured, boolean isMovie);

    List<Movie> findByNameContainingIgnoreCaseAndIsMovie(String keyword, boolean isMovie);
}
