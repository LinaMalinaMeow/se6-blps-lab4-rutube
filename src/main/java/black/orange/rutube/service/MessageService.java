package black.orange.rutube.service;

import black.orange.rutube.entity.Video;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    public String getReviewTitle() {
        return "Напоминание о ревью!";
    }

    public String getNeedReviewBody(List<Video> videos) {
        StringBuilder message = new StringBuilder("Добрый вечер, модератор!\n");
        message.append("Пора бы уже проверить вот эти видео:\n");
        message.append("Id\tLink\n");
        for (Video video : videos) {
            message.append(video.getId()).append("\t").append(video.getLink()).append("\n");
        }
        return String.valueOf(message);
    }

    public String getModeratorTitle() {
        return "Твоё видео оценил модератор";
    }

    public String getModerBody() {
        return "Пора бы зайти и посмотреть чу там!!!";
    }
}
