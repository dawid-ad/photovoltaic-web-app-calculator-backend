package pl.dawad.backend.validation;

import org.springframework.stereotype.Component;
import pl.dawad.backend.exception.IncompleteFormDataProvidedException;
import pl.dawad.backend.model.ContactForm;
import pl.dawad.backend.model.CalculationDataSettings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomValidator {
    public void validateContactFormForCalculation(ContactForm contactForm) {
        validate(contactForm, ValidateForCalculation.class,"Following fields are missing or invalid for calculation: ");
    }
    public void validateContactFormForSubmission(ContactForm contactForm) {
        validate(contactForm, ValidateForSubmission.class,"Following fields are missing or invalid for submission: ");
    }
    public void validateGrossMarginSettings(CalculationDataSettings calculationDataSettings) {
        validate(calculationDataSettings, ValidateForCalculation.class,"Following fields are missing or invalid for calculation: ");
    }

    private void validate(Object object, Class<? extends Annotation> annotationClass, String errorMessage){
        List<String> missingFields = new ArrayList<>();
        boolean expectedPvPowerisEmpty = false;
        boolean energyConsumptionPerYearisEmpty = false;

        Field[] fields = object.getClass().getDeclaredFields();


        for (Field field : fields) {
            if (!field.isAnnotationPresent(annotationClass)) {
                continue;
            }

            try {
                field.setAccessible(true);
                Object value = field.get(object);

                if (value == null || isEmptyString(value)) {
                    if (field.getName().equals("expectedPvPower")){
                        expectedPvPowerisEmpty = true;
                    } else if(field.getName().equals("energyConsumptionPerYear")){
                        energyConsumptionPerYearisEmpty = true;
                    } else {
                        missingFields.add(field.getName());
                    }
                } else if ((value instanceof BigDecimal) && (((BigDecimal) value).compareTo(BigDecimal.ZERO) <= 0)) {
                    missingFields.add(field.getName() + " must be greater than zero");
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
        }

        if (expectedPvPowerisEmpty && energyConsumptionPerYearisEmpty) {
            missingFields.add("Expected PV Power or Energy Consumption must be provided!");
        }

        if (!missingFields.isEmpty()) {
            throw new IncompleteFormDataProvidedException(errorMessage + missingFields);
        }
    }
    private boolean isEmptyString(Object value) {
        return value instanceof String && ((String) value).isEmpty();
    }
}
