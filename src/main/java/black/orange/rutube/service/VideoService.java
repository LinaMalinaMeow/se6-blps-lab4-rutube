package black.orange.rutube.service;

import black.orange.rutube.converter.VideoConverter;
import black.orange.rutube.dto.VideoDTO;
import black.orange.rutube.entity.Video;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VideoService {
    private VideoConverter videoConverter;
    private VideoRepository videoRepository;

    public Video addVideo(VideoDTO videoDTO) {

        Video video = videoConverter.toEntity(videoDTO);
        return videoRepository.save(video);

    }
}
