package black.orange.rutube.service;

import black.orange.rutube.config.MessageTemplatesConfig;
import black.orange.rutube.entity.Video;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageTemplatesConfig messageTemplatesConfig;

    public String getReviewTitle() {
        return messageTemplatesConfig.getReviewTitle();
    }

    public String getNeedReviewBody(List<Video> videos) {
        StringBuilder message = new StringBuilder(messageTemplatesConfig.getModerHello());
        message.append(messageTemplatesConfig.getModerCheck());
        message.append(messageTemplatesConfig.getModerHeader());
        for (Video video : videos) {
            message.append(video.getId()).append("\t").append(video.getLink()).append("\n");
        }
        return String.valueOf(message);
    }

    public String getModeratorTitle() {
        return messageTemplatesConfig.getModerTitle();
    }

    public String getModerBody() {
        return messageTemplatesConfig.getModerBody();
    }
}
