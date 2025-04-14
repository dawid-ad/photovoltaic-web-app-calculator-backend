package pl.dawad.backend.email;

import pl.dawad.backend.model.dto.ContactFormRequestDto;

public interface EmailBuilder {
    String getSubject(ContactFormRequestDto data);
    String getBody(ContactFormRequestDto data);
    String getRecipient(ContactFormRequestDto data);
}

