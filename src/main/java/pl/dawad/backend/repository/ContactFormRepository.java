package pl.dawad.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawad.backend.model.entity.ContactForm;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
