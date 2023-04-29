package black.orange.rutube.service;

import black.orange.rutube.converter.VideoConverter;
import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.exception.auth.EntityAlreadyExistsException;
import black.orange.rutube.service.db.VideoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VideoService {
    private final String ENTITY_CLASS_NAME = "Видео";
    private final VideoConverter videoConverter;
    private final VideoDbService videoDbService;
    private final UserService userService;

    public Video addVideo(VideoDto videoDTO) {
        if (videoDbService.existsByLink(videoDTO.getLink())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        Long userId = userService.getUserIdFromContext();

        Video video = videoConverter.toEntity(videoDTO, userId);
        return videoDbService.save(video);
    }

    public Video updateVideo(VideoDto videoDTO) {
        Video video = videoDbService.findByLinkAndUserId(videoDTO.getLink(), userService.getUserIdFromContext());
        video.setName(videoDTO.getName());
        return videoDbService.save(video);
    }

    public void deleteVideo(VideoDto videoDTO) {
        Video video = videoDbService.findByLinkAndUserId(videoDTO.getLink(), userService.getUserIdFromContext());
        videoDbService.delete(video);
    }

    public List<Video> getVideosByVideoStatus(VideoStatus videoStatus) {
        return videoDbService.findAllByVideoStatus(videoStatus);
    }
}
