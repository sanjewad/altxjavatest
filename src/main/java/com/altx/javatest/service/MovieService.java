package com.altx.javatest.service;

import com.altx.javatest.api.dto.MovieCreateRequestDto;
import com.altx.javatest.exceptions.ActorNotFoundException;
import com.altx.javatest.exceptions.MovieNotFoundException;
import com.altx.javatest.model.Actor;
import com.altx.javatest.model.Movie;
import com.altx.javatest.repository.ActorRepository;
import com.altx.javatest.repository.MovieRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    MovieService(@NonNull MovieRepository movieRepository, @NonNull ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Transactional
    public Movie createMovie(MovieCreateRequestDto movieCreateRequestDto) {
        Set<Actor> actors = new LinkedHashSet<>();
        movieCreateRequestDto.getStarIds().forEach(
                st-> {
                    Optional<Actor> actorOpt =  actorRepository.findById(st.longValue());
                    Actor actor = actorOpt.orElseThrow(() -> new ActorNotFoundException(null));
                    actors.add(actor);
                }
        );
        Movie movie = new Movie();
        movie.setTitle(movieCreateRequestDto.getTitle());
        movie.setRunningTimeMins(movieCreateRequestDto.getRunningTimeMins());
        movie.setStars(actors);

        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}
