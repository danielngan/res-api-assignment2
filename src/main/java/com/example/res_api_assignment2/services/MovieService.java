package com.example.res_api_assignment2.services;

import com.example.res_api_assignment2.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.res_api_assignment2.repositories.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie addBook(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteBookById(String id) {
        Optional<Movie> optionalBook = movieRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        movieRepository.deleteById(id);
    }

    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }





    public void deleteMovieById(String id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        movieRepository.deleteById(id);
    }

    public List<Movie> getMoviesOnly() {
        return movieRepository.findByIsMovie(true);
    }

    public List<Movie> getTVShowsOnly() {
        return movieRepository.findByIsMovie(false);
    }

    public List<Movie> getFeaturedMovies() {
        return movieRepository.findByIsFeaturedAndIsMovie(true, true);
    }

    public List<Movie> getFeaturedTVShows() {
        return movieRepository.findByIsFeaturedAndIsMovie(true, false);
    }

    public List<Movie> searchBooksByTitle(String keyword) {
        return movieRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Movie> getBooksByPublishedYear(int year) {
        throw new RuntimeException("Not supported yet.");
    }
}
