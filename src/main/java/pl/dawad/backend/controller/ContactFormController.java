package pl.dawad.backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.service.ContactFormService;

@RestController
@RequestMapping("/public/contact-form")
public class ContactFormController {
    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    @PostMapping("/submit")
    public ContactForm submitContactForm(@RequestBody ContactForm contactForm) {
        contactFormService.processContactForm(contactForm);
        return contactForm;
    }
    @GetMapping
    public String showTestCommunicat(){
        return "No data";
    }

    @GetMapping("/{id}")
    public ContactForm getContactFormById(@PathVariable Long id) {
        return contactFormService.getContactFormById(id);
    }
}
