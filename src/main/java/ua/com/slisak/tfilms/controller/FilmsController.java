package ua.com.slisak.tfilms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.slisak.tfilms.entities.Film;
import ua.com.slisak.tfilms.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class FilmsController {

    private FilmRepository filmRepository;

    @GetMapping("/films/list")
    public String showAllFilms(Model model) {
        List<Film> films = filmRepository.findAll();

        model.addAttribute("list", films);

        return "films/list";
    }

    @GetMapping("/films/add")
    public String showAdd() {
        return "/films/add";
    }

    @PostMapping("/films/add")
    public String addFilm(@RequestParam String name, @RequestParam String genre,
                          @RequestParam int year, @RequestParam double mark,
                          @RequestParam double price) {

        Film film = new Film();
        film.setName(name);
        film.setGenre(genre);
        film.setYear(year);
        film.setMark(mark);
        film.setPrice(price);

        filmRepository.save(film);
        return "redirect:/films/list";
    }

    @GetMapping("/films/edit/{id}")
    public String showEdit(@PathVariable int id, Model model) {
        Optional<Film> f = filmRepository.findById(id);
        if (f.isPresent()) {
            model.addAttribute("film", f.get());
            return "/films/edit";
        }
        model.addAttribute("message", "No film found with id = " + id);
        return "/error";
    }

    @GetMapping("/films/delete/{id}")
    public String deleteFilmById(@PathVariable int id) {
        filmRepository.deleteById(id);
        return "redirect:/films/list";
    }

    @PostMapping("/films/edit")
    public String editFilm(@RequestParam int id, @RequestParam String name, @RequestParam String genre,
                           @RequestParam int year, @RequestParam double mark, @RequestParam double price,
                           Model model) {

        Optional<Film> f = filmRepository.findById(id);
        if (f.isPresent()) {
            Film film = f.get();
            film.setName(name);
            film.setGenre(genre);
            film.setYear(year);
            film.setMark(mark);
            film.setPrice(price);

            filmRepository.save(film);
            return "redirect:/films/list";
        }
        model.addAttribute("message", "No film found with id = " + id);
        return "/error";
    }
}
