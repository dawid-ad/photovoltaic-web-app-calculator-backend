package pl.dawad.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.model.entity.ContactForm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormRequestDto {
    private ContactForm contactForm;
    private CalculationFormData calculationFormData;
    private CalculationResult calculationResult;
}
