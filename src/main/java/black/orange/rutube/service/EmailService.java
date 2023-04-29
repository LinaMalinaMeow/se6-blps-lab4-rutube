package black.orange.rutube.service;

import black.orange.rutube.config.EmailConfig;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;
    private final MessageService messageService;

    public void sendModeratorMessage(String emailTo) {
        send(emailTo, messageService.getModeratorTitle(), messageService.getModerBody());
    }

    public void sendReviewNotification(String emailTo, String message) {
        send(emailTo, messageService.getReviewTitle(), message);
    }

    private void send(String emailTo, String title, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailConfig.getUsername());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(title);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
