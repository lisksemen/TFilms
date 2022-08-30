package ua.com.slisak.tfilms.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice implements PriceInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "customer", nullable = false, length = 100)
    private String customer;

    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToMany
    @JoinTable(name = "invoice_films",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "films_id"))
    private Set<Film> films = new LinkedHashSet<>();

    public void recalculateTotal() {
        total = films.stream()
                .mapToDouble(Film::getPrice)
                .sum();
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
        recalculateTotal();
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Double getPrice() {
        return total;
    }

}