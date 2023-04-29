package black.orange.rutube.kafka;

import black.orange.rutube.config.KafkaConfig;
import black.orange.rutube.converter.MailAvroConverter;
import black.orange.rutube.dto.avro.MailAvro;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProducerService {
    private final KafkaTemplate<Object, MailAvro> template;
    private final KafkaConfig kafkaConfig;
    private final MailAvroConverter mailAvroConverter;

    public void send(String userEmail) {
        template.send(kafkaConfig.getTopicName(), mailAvroConverter.toAvro(userEmail));
    }

    public void send(String userEmail, String message) {
        MailAvro mailAvro = mailAvroConverter.toAvro(userEmail, message);
        template.send(kafkaConfig.getTopicName(), mailAvro);
    }
}
