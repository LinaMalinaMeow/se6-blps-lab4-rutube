package black.orange.rutube.service;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModeratorService {
    private VideoRepository videoRepository;

    public List<Video> getVideos() {
        return videoRepository.findAllByVideoStatus(VideoStatus.REVIEW);
    }

    public Video giveReview(long id, boolean isApproved) {
        Video video = videoRepository.findById(id)
                .orElseThrow(NullPointerException::new);
        if (isApproved) {
            video.setVideoStatus(VideoStatus.APPROVED);
        } else {
            video.setVideoStatus(VideoStatus.REJECTED);
        }
        return videoRepository.save(video);
    }

}
