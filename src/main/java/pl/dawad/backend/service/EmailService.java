package pl.dawad.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.dawad.backend.model.ContactForm;

@Service
public class EmailService {
    @Value("${spring.mail.receiver}")
    private String receiver;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(ContactForm contactForm) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(receiver);
        mail.setFrom(contactForm.getEmail());
        mail.setSubject("New contact form submission from " + contactForm.getName());
        mail.setText(contactForm.getMessage());

        try {
            javaMailSender.send(mail);
            return "Email sent successfully!";
        } catch (MailException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
