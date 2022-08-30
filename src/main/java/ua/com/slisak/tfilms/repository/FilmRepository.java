package ua.com.slisak.tfilms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.slisak.tfilms.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}