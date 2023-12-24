package black.orange.rutube.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
public class KafkaConfig {
    private final String moderatorTopic = "moder-topic";
    private final String reviewTopic = "review-topic";
}
