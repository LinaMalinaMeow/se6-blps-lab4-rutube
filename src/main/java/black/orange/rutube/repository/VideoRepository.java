package black.orange.rutube.repository;

import black.orange.rutube.entity.Video;
import black.orange.rutube.entity.VideoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByVideoStatus(VideoStatus videoStatus);
}
