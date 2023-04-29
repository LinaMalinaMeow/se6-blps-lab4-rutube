package black.orange.rutube.converter;

import black.orange.rutube.dto.avro.MailAvro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailAvroConverter {

    public MailAvro toAvro(String emailTo) {
        return toAvro(emailTo, null);
    }

    public MailAvro toAvro(String emailTo, String message) {
        return MailAvro.newBuilder()
                .setEmailTo(emailTo)
                .setMessage(message)
                .build();
    }
}
