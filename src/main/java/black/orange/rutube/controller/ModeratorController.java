package black.orange.rutube.controller;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class ModeratorController {
    private VideoRepository videoRepository;

    @GetMapping
    public List<Video> getVideos() {
        return videoRepository.findAllByVideoStatus(VideoStatus.REVIEW);
    }

    @PutMapping
    public Video updateVideo(@RequestParam int id, @RequestParam boolean isApproved) {
        Video video = videoRepository.findById(id).orElseThrow(NullPointerException::new);
        if (isApproved) {
            video.setVideoStatus(VideoStatus.APPROVED);
        } else {
            video.setVideoStatus(VideoStatus.REJECTED);
        }
        return videoRepository.save(video);
    }

}
