package black.orange.rutube.service;

import black.orange.rutube.converter.VideoConverter;
import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import black.orange.rutube.exception.auth.EntityAlreadyExistsException;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class VideoService {
    private final String ENTITY_CLASS_NAME = "Видео";
    private VideoConverter videoConverter;
    private VideoRepository videoRepository;
    private UserService userService;

    public Video addVideo(VideoDto videoDTO) {
        if (videoRepository.existsByLink(videoDTO.getLink())) {
            throw new EntityAlreadyExistsException(ENTITY_CLASS_NAME);
        }

        Long userId = userService.getUserIdFromContext();

        Video video = videoConverter.toEntity(videoDTO, userId);
        return videoRepository.save(video);
    }

    public Video updateVideo(VideoDto videoDTO) {
        Video video = videoRepository.findByLinkAndUserId(videoDTO.getLink(), userService.getUserIdFromContext())
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
        video.setName(videoDTO.getName());
        return videoRepository.save(video);
    }

    @Transactional
    public void deleteVideo(VideoDto videoDTO) {
        Video video = videoRepository.findByLinkAndUserId(videoDTO.getLink(), userService.getUserIdFromContext())
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
        videoRepository.delete(video);
    }
}
