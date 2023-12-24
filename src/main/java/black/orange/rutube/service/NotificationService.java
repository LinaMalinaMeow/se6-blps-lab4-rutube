package black.orange.rutube.service;

import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.Video;
import black.orange.rutube.kafka.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final ProducerService producerService;
    private final MessageService messageService;

    public void sendUserNotification(Long userId) {
        String userEmail = userService.getUserEmail(userId);
        producerService.send(userEmail);
    }

    public void sendAdminNotification(List<Video> videos, Role admin) {
        String message = messageService.getNeedReviewBody(videos);
        admin.getUsers().forEach(a -> producerService.send(a.getEmail(), message));
    }
}
