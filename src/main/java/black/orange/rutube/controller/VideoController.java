package black.orange.rutube.controller;

import black.orange.rutube.dto.MessageDto;
import black.orange.rutube.dto.VideoDto;
import black.orange.rutube.entity.Video;
import black.orange.rutube.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/user/video")
public class VideoController {
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<Video> addVideo(@Valid @RequestBody VideoDto videoDTO) {
        return ResponseEntity.ok().body(videoService.addVideoForCurrentUser(videoDTO));
    }

    @DeleteMapping
    public ResponseEntity<MessageDto> deleteVideo(@Valid @RequestBody VideoDto videoDTO) {
        videoService.deleteVideoForCurrentUser(videoDTO);
        return ResponseEntity.ok().body(MessageDto.builder().message("Cущность удалена из бд!!!").build());
    }

    @PutMapping
    public ResponseEntity<Video> updateVideo(@Valid @RequestBody VideoDto videoDTO) {
        return ResponseEntity.ok().body(videoService.updateVideoForCurrentUser(videoDTO));
    }
}
