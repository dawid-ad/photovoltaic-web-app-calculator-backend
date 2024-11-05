package pl.dawad.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.ContactForm;

@Service
public class EmailService {
    @Value("${spring.mail.receiver}")
    private String receiver;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(ContactFormRequestDto contactFormRequestDto) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(receiver);
            mail.setSubject("New contact form: " + contactFormRequestDto.getContactForm().getName() + ", " +
                    contactFormRequestDto.getCalculationResult().getProposedPvPower() + "kWp");
            mail.setText(
                    contactFormRequestDto.getContactForm().toString() + "\n" +
                    contactFormRequestDto.getCalculationFormData().toString() + "\n" +
                    contactFormRequestDto.getCalculationResult().toString()
            );
            javaMailSender.send(mail);
            return true;
        } catch (MailSendException e) {
            return false;
        }
    }
}
