package com.altx.javatest.service;

import com.altx.javatest.exceptions.ActorNotFoundException;
import com.altx.javatest.model.Actor;
import com.altx.javatest.repository.ActorRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    ActorService(@NonNull ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Transactional
    public Actor createActor(Actor actor) {
       return actorRepository.save(actor);
    }

    @Transactional(readOnly = true)
    public Actor getActorById(Long id) {
        return actorRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Transactional
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}
