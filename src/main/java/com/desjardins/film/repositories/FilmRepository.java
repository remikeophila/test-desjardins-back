package com.desjardins.film.repositories;

import com.desjardins.film.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
