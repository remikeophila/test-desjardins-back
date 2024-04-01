package com.desjardins.film.controller;

import com.desjardins.film.domain.Film;
import com.desjardins.film.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("{filmId}")
    public ResponseEntity<Film> getFilm(@PathVariable long filmId) {
        return ResponseEntity.ok(filmService.getFilmById(filmId));
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody Film film) {
        Film savedFilm = filmService.saveFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
    }
}
