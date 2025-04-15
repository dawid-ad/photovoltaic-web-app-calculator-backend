package pl.dawad.backend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.dawad.backend.model.dto.ContactFormRequestDto;
import pl.dawad.backend.model.entity.CalculationFormData;
import pl.dawad.backend.model.entity.CalculationResult;
import pl.dawad.backend.model.entity.ContactForm;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${mail.sender.preview-name}")
    private String senderPreviewName;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(ContactFormRequestDto contactFormRequestDto, EmailBuilder emailBuilder) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailBuilder.getRecipient(contactFormRequestDto));
            helper.setSubject(emailBuilder.getSubject(contactFormRequestDto));
            String plainText = emailBuilder.getBody(contactFormRequestDto);
            String htmlContent = "<div style='font-family: sans-serif; font-size: 14px; line-height: 1.5;'>" +
                    convertHtml(plainText) +
                    "<br><br>Z wyrazami szacunku,<br>" +
                    senderPreviewName + "<br>" +
                    "<img src='cid:footerImage' style='max-width:100%; height:auto;'/>" +
                    "</div>";
            helper.setText(htmlContent, true);
            helper.setFrom(new InternetAddress(sender, senderPreviewName));

            ClassPathResource imageResource = new ClassPathResource("img/email_footer.png");
            helper.addInline("footerImage", imageResource);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Mail send failed: " + e.getMessage());
            return false;
        }
    }

    private String convertHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replaceAll("(\r\n|\n)", "<br>");
    }

}
