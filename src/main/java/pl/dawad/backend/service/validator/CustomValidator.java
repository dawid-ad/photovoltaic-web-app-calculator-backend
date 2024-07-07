package pl.dawad.backend.service.validator;

import org.springframework.stereotype.Component;
import pl.dawad.backend.exception.IncompleteFormDataProvidedException;
import pl.dawad.backend.model.ContactForm;

import java.math.BigDecimal;

@Component
public class CustomValidator {
    public void validateContactForm(ContactForm contactForm) {
        if (contactForm == null) {
            throw new IncompleteFormDataProvidedException("ContactForm cannot be null");
        }

        if (contactForm.getName() == null || contactForm.getName().isEmpty()) {
            throw new IncompleteFormDataProvidedException("Name must be provided");
        }

        if (contactForm.getEmail() == null || contactForm.getEmail().isEmpty()) {
            throw new IncompleteFormDataProvidedException("Email must be provided");
        }
        if (contactForm.getPhone() == null || contactForm.getPhone().isEmpty()) {
            throw new IncompleteFormDataProvidedException("Phone must be provided");
        }

        if (contactForm.getUserType() == null || contactForm.getUserType().isEmpty()) {
            throw new IncompleteFormDataProvidedException("User type must be provided");
        }

        if (contactForm.getRoofType() == null || contactForm.getRoofType().isEmpty()) {
            throw new IncompleteFormDataProvidedException("Roof type must be provided");
        }

        if (contactForm.getRoofConstruction() == null || contactForm.getRoofConstruction().isEmpty()) {
            throw new IncompleteFormDataProvidedException("Roof construction must be provided");
        }

        if (contactForm.getExpectedPvPower().compareTo(BigDecimal.ZERO) <= 0 && contactForm.getEnergyConsumptionPerYear().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncompleteFormDataProvidedException("Expected PV Power or Energy Consumption must be greater than 0");
        }
    }
}
