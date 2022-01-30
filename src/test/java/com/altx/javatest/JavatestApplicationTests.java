package com.altx.javatest;

import com.altx.javatest.api.dto.MovieCreateRequestDto;
import com.altx.javatest.model.Actor;
import com.altx.javatest.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JavatestApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private Set<Long> actorIds = new HashSet<>();
	private Long movieId = null;



	@Test
	public void emptyDatabaseShouldReturnEmpty() throws Exception {

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}


	@Test
	public void emptyActorDatabaseShouldReturnEmpty() throws Exception {

		this.mockMvc.perform(get("/actors")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void deleteActorIfExist() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Actor actor1 = new Actor().setFirstName("Will").setLastName("Ferrell").setDateOfBirth("1967-07-16");
		String jsonStringActor1 = mapper.writeValueAsString(actor1);

		ResultActions postAction1 = this.mockMvc.perform(post("/actor")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonStringActor1));

		postAction1.andDo(print()).andExpect(status().isOk());
		postAction1.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.firstName").value(actor1.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(actor1.getLastName()))
				.andExpect(jsonPath("$.dateOfBirth").value(actor1.getDateOfBirth()));

		postAction1.andDo(res -> {
			Actor act = mapper.readValue(res.getResponse().getContentAsString(), Actor.class);
			actorIds.add(act.getId());
		});

		this.mockMvc.perform(delete("/actor/" +actorIds.toArray()[0] )).andDo(print()).andExpect(status().isOk())
				.andDo(print());

		this.mockMvc.perform(get("/actors")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}


	@Test
	public void addMovieWithMultipleActorsIsSuccessful() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Actor actor1 = new Actor().setFirstName("Will").setLastName("Ferrell").setDateOfBirth("1967-07-16");
		String jsonStringActor1 = mapper.writeValueAsString(actor1);

		ResultActions postAction1 = this.mockMvc.perform(post("/actor")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonStringActor1));

		postAction1.andDo(print()).andExpect(status().isOk());
		postAction1.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.firstName").value(actor1.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(actor1.getLastName()))
				.andExpect(jsonPath("$.dateOfBirth").value(actor1.getDateOfBirth()));


		postAction1.andDo(res -> {
			Actor act = mapper.readValue(res.getResponse().getContentAsString(), Actor.class);
			actorIds.add(act.getId());
		});


		this.mockMvc.perform(get("/actor/"+ actorIds.toArray()[0])).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$").isNotEmpty());


		Actor actor2 = new Actor().setFirstName("Jon").setLastName("Heder").setDateOfBirth("1977-10-26");
		String jsonStringActor2 = mapper.writeValueAsString(actor2);

		ResultActions postAction2 = this.mockMvc.perform(post("/actor")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonStringActor2));

		postAction2.andDo(print()).andExpect(status().isOk());
		postAction2.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.firstName").value(actor2.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(actor2.getLastName()))
				.andExpect(jsonPath("$.dateOfBirth").value(actor2.getDateOfBirth()));


		postAction2.andDo(res -> {
			Actor act = mapper.readValue(res.getResponse().getContentAsString(), Actor.class);
			actorIds.add(act.getId());
		});

		this.mockMvc.perform(get("/actors")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());

		MovieCreateRequestDto mov = new MovieCreateRequestDto().setTitle("Top Gun").setRunningTimeMins(110).setStarIds(actorIds);
		String jsonStringMov = mapper.writeValueAsString(mov);

		ResultActions postActionMov = this.mockMvc.perform(post("/movie")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonStringMov));

		postActionMov.andDo(res -> {
			Movie m = mapper.readValue(res.getResponse().getContentAsString(), Movie.class);
			movieId = m.getId();
		});

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());

		this.mockMvc.perform(get("/movie/" + movieId)).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.title").value(mov.getTitle()))
				.andExpect(jsonPath("$.runningTimeMins").value(mov.getRunningTimeMins()))
				.andExpect(jsonPath("$.stars.*").isNotEmpty());


		postActionMov.andDo(print()).andExpect(status().isOk());
		postActionMov.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.title").value(mov.getTitle()))
				.andExpect(jsonPath("$.runningTimeMins").value(mov.getRunningTimeMins()))
				.andExpect(jsonPath("$.stars[0].id").value(actorIds.toArray()[0]))
				.andExpect(jsonPath("$.stars[0].firstName").value(actor1.getFirstName()))
				.andExpect(jsonPath("$.stars[0].lastName").value(actor1.getLastName()))
				.andExpect(jsonPath("$.stars[0].dateOfBirth").value(actor1.getDateOfBirth()))
				.andExpect(jsonPath("$.stars[1].id").value(actorIds.toArray()[1]))
				.andExpect(jsonPath("$.stars[1].firstName").value(actor2.getFirstName()))
				.andExpect(jsonPath("$.stars[1].lastName").value(actor2.getLastName()))
				.andExpect(jsonPath("$.stars[1].dateOfBirth").value(actor2.getDateOfBirth()));


	}


	@Test
	public void deleteMovieIfExist() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		MovieCreateRequestDto mov = new MovieCreateRequestDto().setTitle("Top Gun").setRunningTimeMins(110).setStarIds(new LinkedHashSet<>());
		String jsonStringMov = mapper.writeValueAsString(mov);

		ResultActions postActionMov = this.mockMvc.perform(post("/movie")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonStringMov));

		postActionMov.andDo(res -> {
			Movie m = mapper.readValue(res.getResponse().getContentAsString(), Movie.class);
			movieId = m.getId();
		});


		this.mockMvc.perform(delete("/movie/" +movieId)).andDo(print()).andExpect(status().isOk())
				.andDo(print());

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());


	}

}
