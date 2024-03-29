package black.orange.rutube.converter;

import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoConverter {
    public Video toEntity(VideoDto videoDTO, Long userId) {
        Video video = new Video();
        video.setName(videoDTO.getName());
        video.setLink(videoDTO.getLink());
        video.setUserId(userId);
        return video;
    }
}
