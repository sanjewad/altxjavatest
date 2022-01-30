package com.altx.javatest.model;

import com.altx.javatest.model.Actor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;

/**
 * Database representation of a Movie
 */

@Data
@Accessors(chain = true)
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@EqualsAndHashCode(exclude="stars")
public class Movie {

    private @Id @GeneratedValue Long id;
    private String title;
    private Integer runningTimeMins;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "movie_actor",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "actor_id") }
    )
    private Set<Actor> stars = new LinkedHashSet<>();
}
