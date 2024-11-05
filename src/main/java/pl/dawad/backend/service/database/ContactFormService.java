package pl.dawad.backend.service.database;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import pl.dawad.backend.exception.ResourceNotFoundException;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.model.entity.ContactForm;
import pl.dawad.backend.repository.ContactFormRepository;

@Service
public class ContactFormService {
    private final ContactFormRepository contactFormRepository;
    private final CalculationFormDataService calculationFormDataService;
    private final CalculationResultService calculationResultService;

    public ContactFormService(ContactFormRepository contactFormRepository,
                              CalculationFormDataService calculationFormDataService,
                              CalculationResultService calculationResultService) {
        this.contactFormRepository = contactFormRepository;
        this.calculationFormDataService = calculationFormDataService;
        this.calculationResultService = calculationResultService;
    }

    public ContactForm saveContactForm(@Valid ContactFormRequestDto contactFormRequestDto) {
        ContactForm contactForm = contactFormRequestDto.getContactForm();
        CalculationFormData savedCalculationFormData = calculationFormDataService.saveCalculationFormData(contactFormRequestDto.getCalculationFormData());
        CalculationResult savedCalculationResult = calculationResultService.saveCalculationResult(contactFormRequestDto.getCalculationResult());
        contactForm.setCalculationFormDataId(savedCalculationFormData.getId());
        contactForm.setCalculationResultId(savedCalculationResult.getId());
        return contactFormRepository.save(contactForm);
    }

    public ContactForm getContactFormById(Long id) {
        return contactFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found with id " + id));
    }

    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }



//    public ContactForm saveContactFormWithCalculationData(ContactForm contactForm, Long calculationFormDataId, Long calculationResultId) {
//        CalculationFormData calculationFormData = calculationFormDataService.getCalculationFormDataById(calculationFormDataId)
//                .orElseThrow(() -> new ResourceNotFoundException("CalculationFormData not found"));
//
//        CalculationResult calculationResult = calculationResultService.getCalculationResultById(calculationResultId)
//                .orElseThrow(() -> new ResourceNotFoundException("CalculationResult not found"));
//
//        contactForm.setCalculationFormData(calculationFormData);
//        contactForm.setCalculationResult(calculationResult);
//
//        return saveContactForm(contactForm);
//    }

}
