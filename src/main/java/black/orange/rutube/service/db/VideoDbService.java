package black.orange.rutube.service.db;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.exception.auth.EntityNotFoundException;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Service
public class VideoDbService {
    private final VideoRepository videoRepository;
    private final String ENTITY_CLASS_NAME = "Видео";


    @Transactional(readOnly = true)
    public boolean existsByLink(String link) {
        return videoRepository.existsByLink(link);
    }

    @Transactional(readOnly = true)
    public Video findByLinkAndUserId(String link, Long userId) {
        return videoRepository.findByLinkAndUserId(link, userId)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public List<Video> findAllByVideoStatus(VideoStatus videoStatus) {
        return videoRepository.findAllByVideoStatus(videoStatus);
    }

    @Transactional(readOnly = true)
    public Video findById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(NullPointerException::new);
    }

    @Transactional
    public Video create(Video video) {
        return videoRepository.save(video);
    }

    @Transactional
    public void delete(Video video) {
        videoRepository.delete(video);
    }


}
