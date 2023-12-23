package black.orange.rutube.controller;

import black.orange.rutube.entity.Video;
import black.orange.rutube.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class AdminController {
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Video>> getVideos() {
        return ResponseEntity.ok().body(adminService.getVideos());
    }

    @PutMapping
    public ResponseEntity<Video> giveReview(@RequestParam long videoId, @RequestParam boolean isApproved) {
        return ResponseEntity.ok().body(adminService.giveReview(videoId, isApproved));
    }
}
