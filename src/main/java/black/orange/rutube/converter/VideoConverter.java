package black.orange.rutube.converter;

import black.orange.rutube.dto.VideoDTO;
import black.orange.rutube.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoConverter {
    public Video toEntity(VideoDTO videoDTO) {
        Video video = new Video();
        video.setName(videoDTO.getName());
        video.setLink(videoDTO.getLink());
        return video;
    }
}
