package com.altx.javatest.exceptions;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(Long id) {super("Could not find account " + id);
    }
}
