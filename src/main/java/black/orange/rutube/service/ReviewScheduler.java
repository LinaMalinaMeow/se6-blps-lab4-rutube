package black.orange.rutube.service;

import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.kafka.ProducerService;
import black.orange.rutube.service.db.RoleDbService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewScheduler {
    private final String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    private final VideoService videoService;
    private final RoleDbService roleDbService;
    private final MessageService messageService;
    private final ProducerService producerService;

    @Scheduled(cron = "*/50 * * * * *")
    @Transactional(readOnly = true)
    public void scheduleReview() {
        List<Video> videos = videoService.getVideosByVideoStatus(VideoStatus.REVIEW);
        if (videos.isEmpty()) return;
        Role roleAdmin = roleDbService.findByName(ROLE_ADMIN_NAME);
        String message = messageService.getNeedReviewBody(videos);
        roleAdmin.getUsers().forEach(admin -> producerService.send(admin.getEmail(), message));
    }
}
