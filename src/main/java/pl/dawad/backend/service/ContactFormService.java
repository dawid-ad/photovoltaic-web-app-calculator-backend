package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.repository.ContactFormRepository;

@Service
public class ContactFormService {
    private final ContactFormRepository contactFormRepository;
    private final PhotovoltaicItemService photovoltaicItemService;
    private final EmailService emailService;

    public ContactFormService(ContactFormRepository contactFormRepository, PhotovoltaicItemService photovoltaicItemService, EmailService emailService) {
        this.contactFormRepository = contactFormRepository;
        this.photovoltaicItemService = photovoltaicItemService;
        this.emailService = emailService;
    }

    public ContactForm processContactForm(ContactForm contactForm) {
        contactFormRepository.save(contactForm);
        emailService.sendEmail(contactForm);
        return contactForm;
    }

    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found with id " + id));
    }
}
