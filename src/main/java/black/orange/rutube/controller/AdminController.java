package black.orange.rutube.controller;

import black.orange.rutube.entity.Video;
import black.orange.rutube.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class AdminController {
    private AdminService adminService;

    @GetMapping
    public List<Video> getVideos() {
        return adminService.getVideos();
    }

    @PutMapping
    public Video giveReview(@RequestParam long videoId, @RequestParam boolean isApproved) {
        return adminService.giveReview(videoId, isApproved);
    }
}
