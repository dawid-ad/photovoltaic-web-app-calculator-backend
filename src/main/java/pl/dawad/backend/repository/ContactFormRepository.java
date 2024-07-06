package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.ContactForm;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
