package ua.com.slisak.tfilms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.slisak.tfilms.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}