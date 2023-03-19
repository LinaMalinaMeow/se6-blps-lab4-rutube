package black.orange.rutube.service;

import black.orange.rutube.converter.VideoConverter;
import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import black.orange.rutube.exception.auth.EntityAlreadyExistsException;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VideoService {
    private final String ENTITY_CLASS_NAME = "Видео";
    private VideoConverter videoConverter;
    private VideoRepository videoRepository;

    public Video addVideo(VideoDto videoDTO) {
        if (videoRepository.existsByLink(videoDTO.getLink())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }
        Video video = videoConverter.toEntity(videoDTO);
        return videoRepository.save(video);
    }

    public Video updateVideo(VideoDto videoDTO) {
        Video video = videoRepository.findByLink(videoDTO.getLink())
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
        video.setName(videoDTO.getName());
        return videoRepository.save(video);
    }
}
