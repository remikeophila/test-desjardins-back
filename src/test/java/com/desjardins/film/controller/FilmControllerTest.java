package com.desjardins.film.controller;

import com.desjardins.film.domain.Film;
import com.desjardins.film.service.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;

    @Test
    public void whenGetAFilm_ShouldReturnTheFilm() throws Exception {
        Film film = new Film();
        film.setId(1L);
        film.setTitre("Star Wars: The Empire Strikes Back");
        film.setDescription("Darth Vader is adamant about turning Luke Skywalker to the dark side.");

        given(filmService.getFilmById(1L)).willReturn(film);

        mockMvc.perform(get("/film/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titre").value("Star Wars: The Empire Strikes Back"))
                .andExpect(jsonPath("$.description").value("Darth Vader is adamant about turning Luke Skywalker to the dark side."));
    }

    @Test
    public void whenPostAFilm_ShouldReturnThePostedFilm() throws Exception {
        Film newFilm = new Film();
        newFilm.setTitre("Star Wars: The Empire Strikes Back");
        newFilm.setDescription("Darth Vader is adamant about turning Luke Skywalker to the dark side.");

        Film persistedFilm = new Film();
        persistedFilm.setId(1L);
        persistedFilm.setTitre(newFilm.getTitre());
        persistedFilm.setDescription(newFilm.getDescription());

        when(filmService.saveFilm(any(Film.class))).thenReturn(persistedFilm);

        mockMvc.perform(post("/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFilm)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titre").value("Star Wars: The Empire Strikes Back"))
                .andExpect(jsonPath("$.description").value("Darth Vader is adamant about turning Luke Skywalker to the dark side."));

    }
}