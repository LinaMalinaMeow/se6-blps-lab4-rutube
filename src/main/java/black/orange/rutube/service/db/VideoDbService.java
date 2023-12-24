package black.orange.rutube.service.db;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import black.orange.rutube.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class VideoDbService {
    private final VideoRepository videoRepository;


    @Transactional(readOnly = true)
    public boolean existsByLink(String link) {
        return videoRepository.existsByLink(link);
    }

    @Transactional(readOnly = true)
    public Optional<Video> findByLinkAndUserId(String link, Long userId) {
        return videoRepository.findByLinkAndUserId(link, userId);
    }

    @Transactional(readOnly = true)
    public List<Video> findAllByVideoStatus(VideoStatus videoStatus) {
        return videoRepository.findAllByVideoStatus(videoStatus);
    }

    @Transactional(readOnly = true)
    public Optional<Video> findById(Long videoId) {
        return videoRepository.findById(videoId);
    }

    @Transactional
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Transactional
    public void delete(Video video) {
        videoRepository.delete(video);
    }


}
