package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.repository.ContactFormRepository;

@Service
public class ContactFormService {
    private final ContactFormRepository contactFormRepository;

    public ContactFormService(ContactFormRepository contactFormRepository) {
        this.contactFormRepository = contactFormRepository;
    }

    public ContactForm saveContactFormInDb(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }

    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found with id " + id));
    }
}
