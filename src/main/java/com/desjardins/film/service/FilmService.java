package com.desjardins.film.service;

import com.desjardins.film.domain.Film;
import com.desjardins.film.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found: " + id));
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}
