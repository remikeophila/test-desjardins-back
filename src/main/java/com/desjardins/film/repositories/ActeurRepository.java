package com.desjardins.film.repositories;

import com.desjardins.film.domain.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActeurRepository extends JpaRepository<Acteur, Long> {
}
