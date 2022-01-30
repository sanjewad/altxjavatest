package com.altx.javatest.api;

import com.altx.javatest.exceptions.ActorNotFoundException;
import com.altx.javatest.model.Actor;
import com.altx.javatest.repository.ActorRepository;
import com.altx.javatest.service.ActorService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {

    private final ActorService actorService;

    ActorController(@NonNull ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping("/actor")
    public Actor newActor(@RequestBody Actor actor) {
        return actorService.createActor(actor);
    }

    @GetMapping("/actor/{id}")
    public Actor getActor(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    @GetMapping("/actors")
    public List<Actor> all() {
        return actorService.getAllActors();
    }

    @DeleteMapping("/actor/{id}")
    void deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
    }
}
