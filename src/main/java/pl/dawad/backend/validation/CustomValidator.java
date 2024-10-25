package pl.dawad.backend.validation;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import pl.dawad.backend.exception.IncompleteFormDataProvidedException;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.ContactForm;
import pl.dawad.backend.model.entity.CalculationDataSettings;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomValidator {
//    public void validateCalculationFormDataForCalculation(CalculationFormData CalculationFormData) {
//        validate(CalculationFormData, "Following fields are missing or invalid for calculation: ");
//    }
//    public void validateContactFormForSubmission(ContactForm contactForm) {
//        validate(contactForm, "Following fields are missing or invalid for submission: ");
//    }
//    public void validateGrossMarginSettings(CalculationDataSettings calculationDataSettings) {
//        validate(calculationDataSettings, "Following fields are missing or invalid for setting gross margins: ");
//    }
//
//    private void validate(Object object, String errorMessage){
//        List<String> missingFields = new ArrayList<>();
//        boolean expectedPvPowerisEmpty = false;
//        boolean energyConsumptionPerYearisEmpty = false;
//
//        Field[] fields = object.getClass().getDeclaredFields();
//
//
//        for (Field field : fields) {
//            if (!field.isAnnotationPresent(NotNull.class)) {
//                continue;
//            }
//
//            try {
//                field.setAccessible(true);
//                Object value = field.get(object);
//
//                if (value == null || isEmptyString(value)) {
//                    if (field.getName().equals("expectedPvPower")){
//                        expectedPvPowerisEmpty = true;
//                    } else if(field.getName().equals("energyConsumptionPerYear")){
//                        energyConsumptionPerYearisEmpty = true;
//                    } else {
//                        missingFields.add(field.getName());
//                    }
//                } else if ((value instanceof BigDecimal) && (((BigDecimal) value).compareTo(BigDecimal.ZERO) <= 0)) {
//                    missingFields.add(field.getName() + " must be greater than zero");
//                }
//            } catch (IllegalAccessException e) {
//                // ignore
//            }
//        }
//
//        if (expectedPvPowerisEmpty && energyConsumptionPerYearisEmpty) {
//            missingFields.add("Expected PV Power or Energy Consumption must be provided!");
//        }
//
//        if (!missingFields.isEmpty()) {
//            throw new IncompleteFormDataProvidedException(errorMessage + missingFields);
//        }
//    }
//    private boolean isEmptyString(Object value) {
//        return value instanceof String && ((String) value).isEmpty();
//    }
}
