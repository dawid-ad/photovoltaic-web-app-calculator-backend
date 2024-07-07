package pl.dawad.backend.service;

import org.springframework.stereotype.Service;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.model.PhotovoltaicItem;
import pl.dawad.backend.service.validator.CustomValidator;

import java.math.BigDecimal;

@Service
public class CalculatorService {
    private final CustomValidator customValidator;
    private final PhotovoltaicItemService photovoltaicItemService;

    public CalculatorService(CustomValidator customValidator, PhotovoltaicItemService photovoltaicItemService) {
        this.customValidator = customValidator;
        this.photovoltaicItemService = photovoltaicItemService;
    }

    public double preprocessCalculation(ContactForm contactForm) {
        customValidator.validateContactForm(contactForm);

        double grossMarginMin = 1.10;
        double grossMarginMax = 1.20;

        BigDecimal expectedPvPower = contactForm.getExpectedPvPower();
        BigDecimal energyConsumptionPerYear = contactForm.getEnergyConsumptionPerYear();
        String roofType = contactForm.getRoofType();
        String roofConstruction = contactForm.getRoofConstruction();
        double pvPower = 0.0;

//        if(expectedPvPower > 0.0){
//            pvPower = expectedPvPower;
//        } else {
//            pvPower = energyConsumptionPerYear * 1.3;
//        }

//        PhotovoltaicItem photovoltaicItem = photovoltaicItemService.getClosestPowerPhotovoltaicItem(pvPower);

        return 0.0;
    }
}
