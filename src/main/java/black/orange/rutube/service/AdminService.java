package black.orange.rutube.service;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.service.db.VideoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final VideoDbService videoDbService;

    public List<Video> getVideos() {
        return videoDbService.findAllByVideoStatus(VideoStatus.REVIEW);
    }

    public Video giveReview(long videoId, boolean isApproved) {
        Video video = videoDbService.findById(videoId);
        if (isApproved) {
            video.setVideoStatus(VideoStatus.APPROVED);
        } else {
            video.setVideoStatus(VideoStatus.REJECTED);
        }
        return videoDbService.create(video);
    }

}
