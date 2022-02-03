package com.altx.javatest.api;

import com.altx.javatest.api.dto.MovieCreateRequestDto;
import com.altx.javatest.exceptions.ActorNotFoundException;
import com.altx.javatest.exceptions.MovieNotFoundException;
import com.altx.javatest.model.Actor;
import com.altx.javatest.model.Movie;
import com.altx.javatest.repository.ActorRepository;
import com.altx.javatest.repository.MovieRepository;
import com.altx.javatest.service.MovieService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MovieController {

    private final MovieService movieService;


    MovieController(@NonNull MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public Movie newMovie(@RequestBody MovieCreateRequestDto movieCreateRequestDto) {

       return movieService.createMovie(movieCreateRequestDto);
    }

    @GetMapping("/movie/{id}")
    public Movie getMovie(@PathVariable Long id) {
       Movie movie =  movieService.getMovieById(id);
        return  movie;
    }

    @GetMapping("/movies")
    public List<Movie> all() {
        return movieService.getAllMovies();
    }

    @DeleteMapping("/movie/{id}")
    void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
