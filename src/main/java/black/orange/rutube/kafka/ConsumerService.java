package black.orange.rutube.kafka;

import black.orange.rutube.dto.avro.MailAvro;
import black.orange.rutube.service.EmailService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsumerService {
    private final EmailService emailService;

    @KafkaListener(topics = {"email-topic"})
    public void listenTopic(ConsumerRecord<String, MailAvro> record) {
        String userEmail = String.valueOf(record.value().getEmailTo());
        String message = String.valueOf(record.value().getMessage());
        if (message.equals("null")) {
            emailService.sendModeratorMessage(userEmail);
        } else {
            emailService.sendReviewNotification(userEmail, message);
        }
    }
}
