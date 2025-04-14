package pl.dawad.backend.email;

import org.springframework.stereotype.Component;
import pl.dawad.backend.model.dto.ContactFormRequestDto;

@Component
public class CustomerEmailBuilder implements EmailBuilder {
    @Override
    public String getSubject(ContactFormRequestDto data) {
        return "Twoje zapytanie o instalację fotowoltaiczną";
    }

    @Override
    public String getBody(ContactFormRequestDto data) {
        return "Drogi kliencie,\n\n" +
                "Dziękujemy za przesłanie zapytania.\n" +
                "\n" +
                "Skontakujemy się z Tobą telefonicznie, aby przedstawić szczegóły i odpowiedzieć na wszelkie pytania związane z Twoją kalkulacją. Zazwyczaj oddzwaniamy w ciągu jednego dnia roboczego.\n" +
                "\n" +
                "Jeśli nie chcesz czekać – możesz zadzwonić do nas wcześniej. Dane kontaktowe opiekuna Twojej oferty, znajdziesz w stopce tej wiadomości.\n" +
                "\n" +
                "Dziękujemy za zaufanie i do usłyszenia!";
    }

    @Override
    public String getRecipient(ContactFormRequestDto data) {
        return data.getContactForm().getEmail();
    }
}
