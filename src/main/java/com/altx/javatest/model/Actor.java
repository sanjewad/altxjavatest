package com.altx.javatest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@EqualsAndHashCode(exclude="movies")
public class Actor {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    @JsonIgnore
    @ManyToMany(mappedBy = "stars")
    private Set<Movie> movies = new LinkedHashSet<>();
}
