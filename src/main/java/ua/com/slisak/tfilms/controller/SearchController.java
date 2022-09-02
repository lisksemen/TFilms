package ua.com.slisak.tfilms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.slisak.tfilms.entities.Film;
import ua.com.slisak.tfilms.entities.Invoice;
import ua.com.slisak.tfilms.services.SearchService;
import ua.com.slisak.tfilms.services.SearchStrategy;

import java.util.List;

/**
 * Controller for search operations
 */
@Controller
@AllArgsConstructor
public class SearchController {

    SearchService searchService;

    @GetMapping("/search")
    public String showSearchPage() {
        return "../static/search";
    }

    @GetMapping("/search/invoices/most_expensive")
    public String showMostExpensiveInvoice(Model model) {
        model.addAttribute("list", searchService.getTheMostExpensiveInvoice());
        return "/invoices/list";
    }

    @GetMapping("/search/invoices/least_expensive")
    public String showLeastExpensiveInvoice(Model model) {
        model.addAttribute("list", searchService.getTheLeastExpensiveInvoice());
        return "/invoices/list";
    }

    @GetMapping("/search/films/most_expensive")
    public String showMostExpensiveFilm(Model model) {

        model.addAttribute("list", searchService.getTheMostExpensiveFilm());
        return "/films/list";
    }

    @GetMapping("/search/films/least_expensive")
    public String showLeastExpensiveFilm(Model model) {

        model.addAttribute("list", searchService.getTheLeastExpensiveFilm());
        return "/films/list";
    }

    @GetMapping("/search/films/by_parameters")
    public String showSearchByParametersPage() {
        return "../static/search_by_parameters";
    }

    @PostMapping("/search/films/by_parameters/by_name")
    public String searchFilmsByName(@RequestParam String film_name, Model model) {
        List<Film> list = searchService.searchFilmsByName(film_name);
        model.addAttribute("list", list);
        return "/films/list";
    }

    @PostMapping("/search/films/by_parameters/by_price")
    public String searchFilmsByParams(@RequestParam double film_number, @RequestParam String film_param, Model model) {
        System.out.println();
        SearchStrategy<Film> strategy = switch (film_param) {
            case "BIGGER" -> SearchStrategy.FILMS_BIGGER_THAN;
            case "EQUALS" -> SearchStrategy.FILMS_EQUALS_TO;
            default -> SearchStrategy.FILMS_LESS_THAN;
        };
        List<Film> list = searchService.searchByParams(strategy, film_number);
        model.addAttribute("list", list);
        return "/films/list";
    }

    @PostMapping("/search/invoices/by_parameters/by_id")
    public String searchInvoicesByName(@RequestParam int invoiceId, Model model) {
        List<Invoice> list = searchService.searchInvoicesById(invoiceId);
        model.addAttribute("list", list);
        return "/invoices/list";
    }

    @PostMapping("/search/invoices/by_parameters/by_price")
    public String searchInvoicesByParams(@RequestParam("invoice_number") double invoiceNumber,
                                         @RequestParam("invoice_param") String invoiceParam, Model model) {
        SearchStrategy<Invoice> strategy = switch (invoiceParam) {
            case "BIGGER" -> SearchStrategy.INVOICES_BIGGER_THAN;
            case "EQUALS" -> SearchStrategy.INVOICES_EQUALS_TO;
            default -> SearchStrategy.INVOICES_LESS_THAN;
        };
        List<Invoice> list = searchService.searchInvoicesByParams(strategy, invoiceNumber);
        model.addAttribute("list", list);
        return "/invoices/list";
    }

}
