package ua.com.slisak.tfilms.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.slisak.tfilms.entities.Film;
import ua.com.slisak.tfilms.entities.Invoice;
import ua.com.slisak.tfilms.repository.FilmRepository;
import ua.com.slisak.tfilms.repository.InvoiceRepository;

import java.util.*;

@Component
@AllArgsConstructor
public class SearchService {
    InvoiceRepository invoiceRepository;
    FilmRepository filmRepository;

    public List<Invoice> getTheMostExpensiveInvoice() {
        Optional<Invoice> inv = invoiceRepository
                .findAll()
                .stream()
                .max(Comparator.comparing(Invoice::getTotal));
        List<Invoice> result = new ArrayList<>();

        inv.ifPresent(result::add);
        return result;
    }

    public List<Invoice> getTheLeastExpensiveInvoice() {
        Optional<Invoice> inv = invoiceRepository
                .findAll()
                .stream()
                .min(Comparator.comparing(Invoice::getTotal));

        List<Invoice> result = new ArrayList<>();

        inv.ifPresent(result::add);
        return result;
    }

    public List<Film> getTheMostExpensiveFilm() {
        Optional<Film> inv = filmRepository
                .findAll()
                .stream()
                .max(Comparator.comparing(Film::getPrice));

        List<Film> result = new ArrayList<>();

        inv.ifPresent(result::add);
        return result;
    }

    public List<Film> getTheLeastExpensiveFilm() {
        Optional<Film> inv = filmRepository
                .findAll()
                .stream()
                .min(Comparator.comparing(Film::getPrice));
        List<Film> result = new ArrayList<>();

        inv.ifPresent(result::add);
        return result;
    }

    public List<Film> searchFilmsByName(String name) {
        return filmRepository
                .findAll()
                .stream()
                .filter(film -> Objects.equals(film.getName(), name))
                .toList();
    }

    public List<Film> searchByParams(SearchStrategy<Film> strategy, double number) {
        return strategy.search(number, filmRepository);
    }

    public List<Invoice> searchInvoicesById(int invoiceId) {
        return invoiceRepository
                .findAll()
                .stream()
                .filter(invoice -> invoice.getId() == invoiceId)
                .toList();
    }


    public List<Invoice> searchInvoicesByParams(SearchStrategy<Invoice> strategy, double invoiceNumber) {
        return strategy.search(invoiceNumber, invoiceRepository);
    }
}
