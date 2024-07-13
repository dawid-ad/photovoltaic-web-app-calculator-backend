package pl.dawad.backend.controller.public_controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.model.ContactFormResponse;
import pl.dawad.backend.service.ContactFormService;
import pl.dawad.backend.service.EmailService;
import pl.dawad.backend.validation.CustomValidator;

@RestController
@RequestMapping("/public/contact-form")
public class ContactFormController {
    private final ContactFormService contactFormService;
    private final CustomValidator customValidator;
    private final EmailService emailService;

    public ContactFormController(ContactFormService contactFormService, CustomValidator customValidator, EmailService emailService) {
        this.contactFormService = contactFormService;
        this.customValidator = customValidator;
        this.emailService = emailService;
    }

    @PostMapping("/submit")
    public ResponseEntity<ContactFormResponse> submitContactForm(@RequestBody ContactForm contactForm) {
        customValidator.validateContactFormForSubmission(contactForm);
        ContactForm processedForm = contactFormService.saveContactFormInDb(contactForm);
        boolean emailSent = emailService.sendEmail(contactForm);
        String emailStatusMessage = emailSent ? "sent successfully" : "failed!";
        return ResponseEntity.ok(new ContactFormResponse(processedForm, true, "Form status: submitted successfully. Email status: " + emailStatusMessage));
    }

    @GetMapping("/{id}")
    public ContactForm getContactFormById(@PathVariable Long id) {
        return contactFormService.getContactFormById(id);
    }
}
