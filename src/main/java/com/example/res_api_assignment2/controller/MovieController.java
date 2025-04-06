package com.example.res_api_assignment2.controller;

import com.example.res_api_assignment2.model.Movie;
import com.example.res_api_assignment2.repositories.MovieRepository;
import com.example.res_api_assignment2.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")

public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<Movie> getFeatureMovie(@PathVariable int year) {
        return movieService.getBooksByPublishedYear(year);
    }


    public List<Movie> getAllBooks() {
        return movieService.getAllMovies();
    }

    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.addBook(movie);
    }

    @GetMapping("/getMovieById")
    public ResponseEntity<Movie> getMovieById(@RequestParam String id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return ResponseEntity.ok(optionalMovie.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie createdBook = movieService.addBook(movie);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        // Validate if ID exists
        Optional<Movie> optionalBook = movieRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.status(404).body("Book with ID " + id + " not found.");
        }

        // Validate incoming data (basic check)
        if (updatedMovie.getName() == null && updatedMovie.getPrice() == 0 &&
                updatedMovie.getSynopsis() == null && updatedMovie.getRentPrice() == 0 &&
                updatedMovie.getOutrightPrice() == 0 && updatedMovie.getSmallPosterPath() == null &&
                updatedMovie.getLargePosterPath() == null && !updatedMovie.isMovie() &&
                !updatedMovie.isFeatured()) {
            return ResponseEntity.status(400).body("No data provided for update.");
        }


        // Update fields
        Movie movie = optionalBook.get();
        movie.setName(updatedMovie.getName());
        movie.setPrice(updatedMovie.getPrice());
        movie.setSynopsis(updatedMovie.getSynopsis());
        movie.setRentPrice(updatedMovie.getRentPrice());
        movie.setOutrightPrice(updatedMovie.getOutrightPrice());


        Movie saved = movieRepository.save(movie);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/getMoviesOnly")
    public List<Movie> getMoviesOnly() {
        List<Movie> movies = movieService.getMoviesOnly();
        return movies;
    }

    @GetMapping("/getTVShowsOnly")
    public List<Movie> getTVShowsOnly() {
        List<Movie> tvShows = movieService.getTVShowsOnly();
        return tvShows;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable String id) {
        if (!movieService.existsById(id)) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with ID " + id + " not found.");
        }

        movieService.deleteMovieById(id);
        return "Movie with ID " + id + " has been deleted.";
    }

    @GetMapping("/search")
    public List<Movie> searchBooks(@RequestParam String title) {
        return movieService.searchBooksByTitle(title);
    }

    @GetMapping("/getFeaturedMovies")
    public List<Movie> getFeaturedMovies() {
        return movieService.getFeaturedMovies();
    }

    @GetMapping("/getFeaturedTVShows")
    public List<Movie> getFeaturedTVShow() {
        return movieService.getFeaturedTVShows();
    }

}
