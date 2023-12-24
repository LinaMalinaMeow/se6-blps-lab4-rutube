package black.orange.rutube.service;

import black.orange.rutube.entity.Role;
import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.exception.EntityNotFoundException;
import black.orange.rutube.service.db.RoleDbService;
import black.orange.rutube.service.db.VideoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NotifyAdminsForReviewTask {
    private final static String ENTITY_ROLE_CLASS_NAME = "Роль";

    private final VideoDbService videoDbService;
    private final RoleDbService roleDbService;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public void start() {
        List<Video> videos = videoDbService.findAllByVideoStatus(VideoStatus.REVIEW);
        if (videos.isEmpty()) return;
        Role admin = roleDbService.findAdminRole().orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
        notificationService.sendAdminNotification(videos, admin);
    }
}
