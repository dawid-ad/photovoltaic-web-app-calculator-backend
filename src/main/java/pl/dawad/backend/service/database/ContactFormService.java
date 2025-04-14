package pl.dawad.backend.service.database;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.model.entity.ContactForm;
import pl.dawad.backend.repository.ContactFormRepository;
import pl.dawad.backend.email.CustomerEmailBuilder;
import pl.dawad.backend.email.EmailService;
import pl.dawad.backend.email.InternalEmailBuilder;

@Service
public class ContactFormService {
    private final ContactFormRepository contactFormRepository;
    private final CalculationFormDataService calculationFormDataService;
    private final CalculationResultService calculationResultService;
    private final EmailService emailService;
    private final InternalEmailBuilder internalBuilder;
    private final CustomerEmailBuilder customerBuilder;

    public ContactFormService(ContactFormRepository contactFormRepository,
                              CalculationFormDataService calculationFormDataService,
                              CalculationResultService calculationResultService,
                              EmailService emailService,
                              InternalEmailBuilder internalBuilder,
                              CustomerEmailBuilder customerBuilder) {
        this.contactFormRepository = contactFormRepository;
        this.calculationFormDataService = calculationFormDataService;
        this.calculationResultService = calculationResultService;
        this.emailService = emailService;
        this.internalBuilder = internalBuilder;
        this.customerBuilder = customerBuilder;
    }

    public ContactForm saveContactForm(@Valid ContactFormRequestDto contactFormRequestDto) {
        ContactForm contactForm = contactFormRequestDto.getContactForm();
        CalculationFormData savedCalculationFormData = calculationFormDataService.saveCalculationFormData(contactFormRequestDto.getCalculationFormData());
        CalculationResult savedCalculationResult = calculationResultService.saveCalculationResult(contactFormRequestDto.getCalculationResult());
        contactForm.setCalculationFormDataId(savedCalculationFormData.getId());
        contactForm.setCalculationResultId(savedCalculationResult.getId());
        return contactFormRepository.save(contactForm);
    }

    public boolean sendEmailWithNewContactForm(ContactFormRequestDto dto){
        return emailService.sendEmail(dto, internalBuilder) && emailService.sendEmail(dto, customerBuilder);
    }

    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found with id " + id));
    }

    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }

}
