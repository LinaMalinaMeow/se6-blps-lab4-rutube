package black.orange.rutube.converter;

import black.orange.rutube.dto.avro.MailAvro;
import org.springframework.stereotype.Component;

@Component
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
