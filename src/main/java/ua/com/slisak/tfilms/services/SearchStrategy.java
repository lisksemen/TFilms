package ua.com.slisak.tfilms.services;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.slisak.tfilms.entities.Film;
import ua.com.slisak.tfilms.entities.Invoice;
import ua.com.slisak.tfilms.entities.PriceInterface;

import java.util.List;

/**
 * Implemented strategy pattern
 * @param <E> Entity with getPrice() method
 */
public interface SearchStrategy<E extends PriceInterface> {
    List<E> search(double number, JpaRepository<E, Integer> repository);

    /**
     * Films
     */
    SearchStrategy<Film> FILMS_BIGGER_THAN = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(object -> object.getPrice() > number)
                    .toList()
    );

    SearchStrategy<Film> FILMS_EQUALS_TO = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(film -> film.getPrice() == number)
                    .toList()
    );

    SearchStrategy<Film> FILMS_LESS_THAN = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(film -> film.getPrice() < number)
                    .toList()
    );

    /**
     * Invoices
     */
    SearchStrategy<Invoice> INVOICES_BIGGER_THAN = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(object -> object.getPrice() > number)
                    .toList()
    );

    SearchStrategy<Invoice> INVOICES_EQUALS_TO = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(film -> film.getPrice() == number)
                    .toList()
    );

    SearchStrategy<Invoice> INVOICES_LESS_THAN = (
            (number, repository) -> repository
                    .findAll()
                    .stream()
                    .filter(film -> film.getPrice() < number)
                    .toList()
    );
}
