package black.orange.rutube.service;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.exception.EntityNotFoundException;
import black.orange.rutube.service.db.VideoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final String ENTITY_CLASS_NAME = "Видео";

    private final VideoDbService videoDbService;
    private final NotificationService notificationService;

    public List<Video> getVideos() {
        return videoDbService.findAllByVideoStatus(VideoStatus.REVIEW);
    }

    public Video giveReview(long videoId, boolean isApproved) {
        Video video = videoDbService.findById(videoId).orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
        ;
        setStatus(isApproved, video);
        video = videoDbService.save(video);
        notificationService.sendUserNotification(video.getUserId());
        return video;
    }

    private static void setStatus(boolean isApproved, Video video) {
        VideoStatus status = isApproved ? VideoStatus.APPROVED : VideoStatus.REJECTED;
        video.setVideoStatus(status);
    }
}
