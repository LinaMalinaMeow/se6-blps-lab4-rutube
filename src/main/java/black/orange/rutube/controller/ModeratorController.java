package black.orange.rutube.controller;

import black.orange.rutube.entity.Video;
import black.orange.rutube.service.ModeratorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class ModeratorController {
    private ModeratorService moderatorService;

    @GetMapping
    public List<Video> getVideos() {
        return moderatorService.getVideos();
    }

    @PutMapping
    public Video giveReview(@RequestParam long id, @RequestParam boolean isApproved) {
        return moderatorService.giveReview(id, isApproved);
    }
}
