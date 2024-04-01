package com.desjardins.film.service;

import com.desjardins.film.domain.Film;
import com.desjardins.film.repositories.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(filmRepository);
    }

    @Test
    public void whenGetFilmById_thenReturnFilm() {
        Long filmId = 1L;
        Film film = new Film();
        film.setId(filmId);
        film.setTitre("Star Wars");
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Film foundFilm = filmService.getFilmById(filmId);

        assertNotNull(foundFilm);
        assertEquals(filmId, foundFilm.getId());
        assertEquals("Star Wars", foundFilm.getTitre());
    }

    @Test
    public void whenGetFilmById_thenThrowNotFoundException() {
        Long filmId = 1L;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            filmService.getFilmById(filmId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Film not found: " + filmId, exception.getReason());
    }

    @Test
    public void whenSaveFilm_thenReturnSavedFilm() {
        Film film = new Film();
        film.setTitre("Star Wars: The Empire Strikes Back");
        film.setDescription("Darth Vader is adamant about turning Luke Skywalker to the dark side.");
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film savedFilm = filmService.saveFilm(film);

        assertNotNull(savedFilm);
        assertEquals("Star Wars: The Empire Strikes Back", savedFilm.getTitre());
        verify(filmRepository).save(film);
    }
}
