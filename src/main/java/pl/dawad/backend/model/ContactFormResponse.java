package pl.dawad.backend.model;

import lombok.Getter;
import lombok.Setter;
import pl.dawad.backend.model.entity.ContactForm;

@Getter
@Setter
public class ContactFormResponse {
    private ContactForm contactForm;
    private boolean success;
    private String message;

    public ContactFormResponse(ContactForm contactForm, boolean success, String message) {
        this.contactForm = contactForm;
        this.success = success;
        this.message = message;
    }
}
