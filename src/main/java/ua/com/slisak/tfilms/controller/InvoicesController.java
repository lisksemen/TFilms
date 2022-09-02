package ua.com.slisak.tfilms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.slisak.tfilms.entities.Film;
import ua.com.slisak.tfilms.entities.Invoice;
import ua.com.slisak.tfilms.repository.FilmRepository;
import ua.com.slisak.tfilms.repository.InvoiceRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for CRUD operations with invoice entity
 */
@Controller
@AllArgsConstructor
public class InvoicesController {
    InvoiceRepository invoiceRepository;
    FilmRepository filmRepository;

    @GetMapping("/invoices/list")
    public String showAllInvoices(Model model) {
        model.addAttribute("list", invoiceRepository.findAll());
        return "/invoices/list";
    }

    @GetMapping("/invoices/add")
    public String showAdd(Model model) {
        model.addAttribute("list", filmRepository.findAll());
        return "/invoices/add";
    }
    @PostMapping("/invoices/add")
    public String addInvoice(@RequestParam Map<String, String> params) {
        Invoice invoice = new Invoice();
        String customer = params.get("customer");
        params.remove("customer");
        invoice.setCustomer(customer);

        params.keySet().stream()
                .mapToInt(Integer::parseInt)
                .mapToObj(filmRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(invoice.getFilms()::add);

        invoice.recalculateTotal();

        invoiceRepository.save(invoice);

        return "redirect:/invoices/list";
    }

    @GetMapping("/invoices/delete/{id}")
    public String deleteInvoiceById(@PathVariable int id) {
        invoiceRepository.deleteById(id);
        return "redirect:/invoices/list";
    }
    @GetMapping("/invoices/edit/{id}")
    public String showEdit(@PathVariable int id, Model model) {
        Optional<Invoice> inv = invoiceRepository.findById(id);
        if (inv.isPresent()) {
            model.addAttribute("invoice", inv.get());
            model.addAttribute("list", filmRepository.findAll());
            return "/invoices/edit";
        }
        return "/error";
    }

    @PostMapping("/invoices/edit/")
    public String editInvoice(@RequestParam Map<String, String> params) {
        int id = Integer.parseInt(params.get("id"));
        params.remove("id");
        Optional<Invoice> inv = invoiceRepository.findById(id);
        if (inv.isPresent()) {
            String customer = params.get("customer");
            params.remove("customer");
            Invoice invoice = inv.get();
            invoice.setCustomer(customer);

            Set<Film> films = params.keySet().stream()
                    .map(Integer::parseInt)
                    .map(filmRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            invoice.setFilms(films);
            invoiceRepository.save(invoice);
            return "redirect:/invoices/list";
        }
        return "/error";
    }

}
