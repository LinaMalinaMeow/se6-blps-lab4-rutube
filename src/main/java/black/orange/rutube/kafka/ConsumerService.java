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

    @KafkaListener(topics = {"moder-topic"})
    public void listenModerTopic(ConsumerRecord<String, MailAvro> record) {
        String userEmail = String.valueOf(record.value().getEmailTo());
        emailService.sendModeratorMessage(userEmail);
    }

    @KafkaListener(topics = {"review-topic"})
    public void listenReviewTopic(ConsumerRecord<String, MailAvro> record) {
        String userEmail = String.valueOf(record.value().getEmailTo());
        String message = String.valueOf(record.value().getMessage());
        emailService.sendReviewNotification(userEmail, message);
    }
}
