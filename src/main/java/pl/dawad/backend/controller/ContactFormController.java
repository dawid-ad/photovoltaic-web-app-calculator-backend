package pl.dawad.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.ContactForm;
import pl.dawad.backend.model.ContactFormResponse;
import pl.dawad.backend.service.database.ContactFormService;
import pl.dawad.backend.service.EmailService;

@RestController
@RequestMapping("/api/contact-form")
@Validated
public class ContactFormController {
    private final ContactFormService contactFormService;
    private final EmailService emailService;

    public ContactFormController(ContactFormService contactFormService, EmailService emailService) {
        this.contactFormService = contactFormService;
        this.emailService = emailService;
    }

    @PostMapping("/submit")
    public ResponseEntity<ContactFormResponse> submitContactForm(@Valid @RequestBody ContactFormRequestDto contactFormRequestDto) {
        ContactForm processedForm = contactFormService.saveContactForm(contactFormRequestDto);
        boolean emailSent = emailService.sendEmail(contactFormRequestDto);
        String emailStatusMessage = emailSent ? "sent successfully" : "failed!";
        return ResponseEntity.ok(new ContactFormResponse(processedForm, emailSent, "Form status: submitted successfully. Email status: " + emailStatusMessage));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_API_KEY')")
    public ContactForm getContactFormById(@PathVariable Long id) {
        return contactFormService.getContactFormById(id);
    }
}
