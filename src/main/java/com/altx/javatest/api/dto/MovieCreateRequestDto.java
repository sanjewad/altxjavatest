package com.altx.javatest.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieCreateRequestDto {
   private String title;
   private Integer runningTimeMins;
   private Set<Long> starIds;

}
