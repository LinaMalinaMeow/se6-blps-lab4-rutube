package black.orange.rutube.controller;

import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import black.orange.rutube.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/user/video")
public class VideoController {
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<Video> addVideo(@Valid @RequestBody VideoDto videoDTO) {
        return ResponseEntity.ok(videoService.addVideo(videoDTO));
    }
}
