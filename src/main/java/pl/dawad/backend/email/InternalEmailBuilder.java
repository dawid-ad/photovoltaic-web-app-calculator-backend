package pl.dawad.backend.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.model.entity.ContactForm;

import java.math.RoundingMode;

@Component
public class InternalEmailBuilder implements EmailBuilder {
    @Value("${spring.mail.internal-receiver}")
    private String internalReceiver;

    @Override
    public String getSubject(ContactFormRequestDto data) {
        return "Nowy klient: " + data.getContactForm().getName() + ", " +
                data.getCalculationResult().getProposedPvPower() + "kWp";
    }

    @Override
    public String getBody(ContactFormRequestDto data) {
        StringBuilder message = new StringBuilder();
        ContactForm contactForm = data.getContactForm();
        CalculationFormData calculationFormData = data.getCalculationFormData();
        CalculationResult calculationResult = data.getCalculationResult();

        message.append("🟢 Nowe zapytanie od klienta\n\n");

        message.append("👤 Dane kontaktowe:\n");
        message.append("Imię i nazwisko: ").append(contactForm.getName()).append("\n");
        message.append("Email: ").append(contactForm.getEmail()).append("\n");
        message.append("Telefon: ").append(contactForm.getPhone()).append("\n");
        message.append("Wiadomość: ").append(contactForm.getMessage()).append("\n");
        message.append("Data kontaktu: ").append(contactForm.getContactDate()).append("\n\n");

        message.append("📋 Formularz kalkulacyjny:\n");
        message.append("Typ klienta: ").append(calculationFormData.getCustomerType()).append("\n");
        message.append("Region: ").append(calculationFormData.getRegion()).append("\n");
        message.append("Typ instalacji: ").append(calculationFormData.getInstallationType()).append("\n");
        if (calculationFormData.getRoofType() != null)
            message.append("Typ dachu: ").append(calculationFormData.getRoofType()).append("\n");
        if (calculationFormData.getRoofSurface() != null)
            message.append("Powierzchnia dachu: ").append(calculationFormData.getRoofSurface()).append("\n");
        if (calculationFormData.getExpectedPvPower() != null)
            message.append("Oczekiwana moc PV: ").append(calculationFormData.getExpectedPvPower()).append(" kW\n");
        if (calculationFormData.getEnergyConsumptionPerYear() != null)
            message.append("Roczne zużycie energii: ").append(calculationFormData.getEnergyConsumptionPerYear()).append(" kWh\n");
        message.append("Projoy: ").append(calculationFormData.isProjoy() ? "TAK" : "NIE").append("\n");
        message.append("Przycisk przeciwpożarowy: ").append(calculationFormData.isFireButton() ? "TAK" : "NIE").append("\n");
        message.append("Optymalizatory mocy: ").append(
                calculationFormData.getPowerOptimizersType() != null ? calculationFormData.getPowerOptimizersType() : "Brak").append("\n");
        message.append("Magazyn energii: ").append(calculationFormData.getEnergyStorageModelId() != null ? "TAK" : "NIE").append("\n");
        message.append("Dotacja: ").append(calculationFormData.isHasGrant() ? "TAK" : "NIE").append("\n\n");

        message.append("🔧 Wynik kalkulacji:\n");
        message.append("Proponowana moc instalacji: ").append(calculationResult.getProposedPvPower()).append(" kW\n");
        message.append("Roczna produkcja energii: ").append(calculationResult.getEstimatedOneYearProduction()).append(" kWh\n");
        message.append("Model falownika: ").append(calculationResult.getInverterModel()).append("\n");
        message.append("Model modułów: ").append(calculationResult.getModuleModel()).append("\n");
        message.append("Moc modułu: ").append(calculationResult.getModulePower()).append(" W\n");
        message.append("Liczba paneli: ").append(calculationResult.getPanelsQuantity()).append("\n");
        message.append("Rodzaj montażu: ").append(calculationResult.getMountTypeForView()).append("\n");
        message.append("Cena z dotacją: ").append(calculationResult.getPrice().setScale(0, RoundingMode.HALF_UP)).append(" zł\n");
        message.append("Cena bez dotacji: ").append(calculationResult.getPriceWithoutGrant().setScale(0, RoundingMode.HALF_UP)).append(" zł\n");
        message.append("Cena za 1 kW: ").append(calculationResult.getPricePerKw().setScale(0, RoundingMode.HALF_UP)).append(" zł\n");
        message.append("Cena energii za kWh: ").append(calculationResult.getEnergyPricePerKwh()).append(" zł\n");
        message.append("Projoy w zestawie: ").append(calculationResult.isProjoyIncluded() ? "TAK" : "NIE").append("\n");
        message.append("Dotacja możliwa: ").append(calculationResult.isGrantPossible() ? "TAK" : "NIE").append("\n");
        message.append("Magazyn energii możliwy: ").append(calculationResult.isEnergyStorageAvailable() ? "TAK" : "NIE").append("\n");

        return message.toString();
    }

    @Override
    public String getRecipient(ContactFormRequestDto data) {
        return internalReceiver;
    }
}
