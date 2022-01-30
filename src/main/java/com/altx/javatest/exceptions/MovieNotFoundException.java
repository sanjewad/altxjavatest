package com.altx.javatest.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}
